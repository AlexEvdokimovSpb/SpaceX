package evdokimov.spacex.details.presentation

import com.github.terrakok.cicerone.Router
import evdokimov.spacex.base.BaseMvpPresenter
import evdokimov.spacex.favorites.domain.FavoritesInteractor
import evdokimov.spacex.favorites.domain.entity.FavoriteLaunch
import evdokimov.spacex.navigation.IScreens
import evdokimov.spacex.news.domain.NewsInteractor
import evdokimov.spacex.news.domain.entity.Launch
import evdokimov.spacex.rx.toBehaviorFlowable
import evdokimov.spacex.rx.withLatestFrom
import evdokimov.spacex.user.domain.UserInteractor
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Named

private const val NUMBER_CHAR_TO_DELETE = 8

class DetailsPresenter(val id: String) : BaseMvpPresenter<DetailsView>() {

    @Inject
    @field:Named("uiScheduler")
    lateinit var uiScheduler: Scheduler

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screens: IScreens

    @Inject
    lateinit var newsInteractor: NewsInteractor

    @Inject
    lateinit var userInteractor: UserInteractor

    @Inject
    lateinit var favoritesInteractor: FavoritesInteractor

    private val actionOnFavoriteIconClickSubject = PublishSubject.create<Unit>()
    private val addToFavoriteSubject = BehaviorSubject.create<Unit>()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        val isUserExists = userInteractor.isUserExists.toBehaviorFlowable()

        val favoritesFlowable = favoritesInteractor.getAll()
                .toBehaviorFlowable()

        val launchFlowable = favoritesFlowable.map { it.contains(FavoriteLaunch(id)) }
                .flatMap { isFavorite ->
                    newsInteractor.getLaunchById(
                            id,
                            isFavorite
                    )
                }

        launchFlowable.subscribeOn(Schedulers.computation())
                .observeOn(uiScheduler)
                .subscribe(::setScreen) {
                    println("Error setDate: ${it.message}")
                }
                .autoDisposable()

        actionOnFavoriteIconClickSubject.toFlowable(BackpressureStrategy.LATEST)
                .withLatestFrom(isUserExists)
                .map { it.second }
                .subscribeOn(Schedulers.computation())
                .observeOn(uiScheduler)
                .subscribe(::checkAndAddToFavorite) {
                    println("Error checkAndAddToFavorite: ${it.message}")
                }
                .autoDisposable()

        addToFavoriteSubject.toFlowable(BackpressureStrategy.LATEST)
                .withLatestFrom(launchFlowable)
                .map { it.second }
                .flatMapSingle { launch -> favoritesInteractor.onFavoriteIconClick(launch) }
                .subscribeOn(Schedulers.computation())
                .observeOn(uiScheduler)
                .subscribe({/* no-op */ },
                        { println("Error addToFavoriteSubject: ${it.message}") })
                .autoDisposable()
    }

    private fun setScreen(launch: Launch) {
        launch.name?.let { viewState.setName(it) }
        launch.links?.flickr?.original?.let {
            if (it.isNotEmpty()) {
                viewState.loadImage(it)
            }
        }
        launch.dateUtc?.let {
            viewState.setDate(
                    it.dropLast(NUMBER_CHAR_TO_DELETE)
                            .replace(
                                    '-',
                                    ' '
                            )
                            .replace(
                                    'T',
                                    ' '
                            )
            )
        }
        launch.details?.let { viewState.setDetails(it) }
        viewState.setFavoriteImage(launch.isFavorite)
    }

    fun onFavoriteIconClick() = actionOnFavoriteIconClickSubject.onNext(Unit)

    private fun checkAndAddToFavorite(isAuthorized: Boolean) {
        if (isAuthorized) {
            addToFavoriteSubject.onNext(Unit)
        } else {
            viewState.showNeedLogIn()
        }
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }
}