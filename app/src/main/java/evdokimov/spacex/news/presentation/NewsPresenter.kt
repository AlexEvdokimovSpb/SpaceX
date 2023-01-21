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

    private val newsSelectSubject = PublishSubject.create<Launch>()
    private val actionUpdateSubject = PublishSubject.create<Unit>()
    private val actionMenuUserClickSubject = PublishSubject.create<Unit>()
    private val actionOnFavoriteIconClickSubject = PublishSubject.create<Launch>()
    private val addToFavoriteSubject = BehaviorSubject.create<Launch>()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        val isUserExists = userInteractor.isUserExists.toBehaviorFlowable()

        isUserExists.subscribeOn(Schedulers.computation())
                .observeOn(uiScheduler)
                .subscribe({
                    viewState.showMessage("suscibe")
                },
                        {
                            println("Error addToFavoriteSubject: ${it.message}")
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
                .subscribe({
                    viewState.showMessage("favorit")
                },
                        {
                            println("Error addToFavoriteSubject: ${it.message}")
                        })
                .autoDisposable()

        newsInteractor.fetchAuthorisedLaunches()
                .subscribeOn(Schedulers.io())
                .observeOn(uiScheduler)
                .subscribe({/* no-op */
                    update()
                },
                        {
                            println("Error fetchAuthorisedLaunches: ${it.message}")
                        })
                .autoDisposable()

        actionUpdateSubject.toFlowable(BackpressureStrategy.LATEST)
                .flatMapSingle {
                    newsInteractor.getAuthorisedLaunches()
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
                .subscribe(::newsSelect) {
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

    fun onNewsSelect(launch: Launch) = newsSelectSubject.onNext(launch)

    fun update() = actionUpdateSubject.onNext(Unit)

    private fun newsSelect(launch: Launch) {
        router.navigateTo(screens.launch(launch))
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

    fun backClick(): Boolean {
        router.exit()
        return true
    }
}