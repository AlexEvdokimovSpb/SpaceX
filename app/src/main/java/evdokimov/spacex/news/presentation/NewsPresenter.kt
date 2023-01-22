package evdokimov.spacex.news.presentation

import com.github.terrakok.cicerone.Router
import evdokimov.spacex.base.BaseMvpPresenter
import evdokimov.spacex.favorites.domain.FavoritesInteractor
import evdokimov.spacex.navigation.IScreens
import evdokimov.spacex.news.domain.NewsInteractor
import evdokimov.spacex.news.domain.entity.Launch
import evdokimov.spacex.rx.toBehaviorFlowable
import evdokimov.spacex.rx.withLatestFrom
import evdokimov.spacex.user.domain.UserInteractor
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.kotlin.combineLatest
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Named

class NewsPresenter : BaseMvpPresenter<NewsView>() {

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

    private val startAuthorisedScreenSubject = PublishSubject.create<Unit>()
    private val startUnauthorisedScreenSubject = PublishSubject.create<Unit>()

    private val newsSelectSubject = PublishSubject.create<Launch>()
    private val actionUpdateSubject = PublishSubject.create<Unit>()

    private val actionMenuUserClickSubject = PublishSubject.create<Unit>()
    private val actionLatestNewsButtonSubject = PublishSubject.create<Unit>()

    private val actionOnFavoriteIconClickSubject = PublishSubject.create<Launch>()
    private val addToFavoriteSubject = BehaviorSubject.create<Launch>()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        val isUserExists = userInteractor.isUserExists.toBehaviorFlowable()

        val favoritesFlowable = favoritesInteractor.getAll()
                .toBehaviorFlowable()

        isUserExists.subscribeOn(Schedulers.io())
                .observeOn(uiScheduler)
                .subscribe(::startScreen) {
                    println("Error isUserExists: ${it.message}")
                }
                .autoDisposable()

        startAuthorisedScreenSubject.toFlowable(BackpressureStrategy.LATEST)
                .flatMapSingle {
                    newsInteractor.fetchAuthorisedLaunches()
                            .andThen(Single.just(Unit))
                }
                .subscribeOn(Schedulers.io())
                .observeOn(uiScheduler)
                .subscribe(
                        {/* no-op */
                        },
                        {
                            println("Error fetchAuthorisedLaunches: ${it.message}")
                        })
                .autoDisposable()

        startUnauthorisedScreenSubject.toFlowable(BackpressureStrategy.LATEST)
                .flatMapSingle {
                    newsInteractor.fetchUnauthorisedLaunches()
                            .andThen(Single.just(Unit))
                }
                .subscribeOn(Schedulers.io())
                .observeOn(uiScheduler)
                .subscribe(
                        {/* no-op */
                        },
                        {
                            println("Error fetchAuthorisedLaunches: ${it.message}")
                        })
                .autoDisposable()

        actionLatestNewsButtonSubject.toFlowable(BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.computation())
                .observeOn(uiScheduler)
                .subscribe({ update() },
                        {
                            println("Error Latest News: ${it.message}")
                        })
                .autoDisposable()

        actionOnFavoriteIconClickSubject.toFlowable(BackpressureStrategy.LATEST)
                .withLatestFrom(isUserExists)
                .subscribeOn(Schedulers.computation())
                .observeOn(uiScheduler)
                .subscribe(::checkAndAddToFavorite) {
                    println("Error checkAndAddToFavorite: ${it.message}")
                }
                .autoDisposable()

        addToFavoriteSubject.toFlowable(BackpressureStrategy.LATEST)
                .flatMapSingle { launch -> favoritesInteractor.onFavoriteIconClick(launch) }
                .subscribeOn(Schedulers.computation())
                .observeOn(uiScheduler)
                .subscribe({/* no-op */ },
                        { println("Error addToFavoriteSubject: ${it.message}") })
                .autoDisposable()

        actionUpdateSubject.toFlowable(BackpressureStrategy.LATEST)
                .combineLatest(favoritesFlowable)
                .flatMap { (_, favorites) ->
                    newsInteractor.getLaunches(favorites)
                }
                .subscribeOn(Schedulers.computation())
                .observeOn(uiScheduler)
                .subscribe(viewState::updateView) {
                    println("Error getAuthorisedLaunches: ${it.message}")
                }
                .autoDisposable()

        newsSelectSubject.toFlowable(BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.computation())
                .observeOn(uiScheduler)
                .subscribe(::newsSelected) {
                    println("Error newsSelect: ${it.message}")
                }
                .autoDisposable()

        actionMenuUserClickSubject.toFlowable(BackpressureStrategy.LATEST)
                .withLatestFrom(isUserExists)
                .map { it.second }
                .subscribeOn(Schedulers.computation())
                .observeOn(uiScheduler)
                .subscribe(::menuUserSelect) {
                    println("Error menuUserSelect: ${it.message}")
                }
                .autoDisposable()
    }

    private fun startScreen(isAuthorized: Boolean) = if (isAuthorized) {
        startAuthorisedScreenSubject.onNext(Unit)
    } else {
        startUnauthorisedScreenSubject.onNext(Unit)
    }

    fun onNewsSelect(launch: Launch) = newsSelectSubject.onNext(launch)

    fun update() = actionUpdateSubject.onNext(Unit)

    private fun newsSelected(launch: Launch) {
        router.navigateTo(screens.launch(launch.id))
    }

    fun onFavoriteIconClick(launch: Launch) = actionOnFavoriteIconClickSubject.onNext(launch)

    private fun checkAndAddToFavorite(dataInfo: Pair<Launch, Boolean>) {
        val (launch, isAuthorized) = dataInfo
        if (isAuthorized) {
            addToFavoriteSubject.onNext(launch)
        } else {
            viewState.showNeedLogIn()
        }
    }

    fun menuUserClick() = actionMenuUserClickSubject.onNext(Unit)

    private fun menuUserSelect(isAuthorized: Boolean) {
        if (isAuthorized) {
            router.navigateTo(screens.profile())
        } else {
            router.navigateTo(screens.authorisation())
        }
    }

    fun latestNewsButtonClick() = actionLatestNewsButtonSubject.onNext(Unit)

    fun backClick(): Boolean {
        router.exit()
        return true
    }
}