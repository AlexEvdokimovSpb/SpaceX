package evdokimov.spacex.user.presentation

import com.github.terrakok.cicerone.Router
import evdokimov.spacex.base.BaseMvpPresenter
import evdokimov.spacex.di.ProdScheduler
import evdokimov.spacex.favorites.domain.FavoritesInteractor
import evdokimov.spacex.navigation.IScreens
import evdokimov.spacex.news.domain.NewsInteractor
import evdokimov.spacex.rx.SchedulerProviderContract
import evdokimov.spacex.user.domain.UserInteractor
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class UserPresenter() : BaseMvpPresenter<UserView>() {

    @Inject
    @ProdScheduler
    lateinit var scheduler: SchedulerProviderContract

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

    private val logOutClickSubject = PublishSubject.create<Unit>()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        userInteractor.getUser()
                .toFlowable()
                .subscribeOn(scheduler.computation())
                .observeOn(scheduler.ui())
                .subscribe(viewState::setUser) {
                    println("Error getUser: ${it.message}")
                }
                .autoDisposable()

        logOutClickSubject.toFlowable(BackpressureStrategy.LATEST)
                .flatMapSingle {
                    userInteractor.deleteUser()
                            .andThen(favoritesInteractor.clear())
                            .andThen(newsInteractor.clear())
                            .andThen(Single.just(Unit))
                }
                .subscribeOn(scheduler.computation())
                .observeOn(scheduler.ui())
                .subscribe({ logOut() },
                        {
                            println("Error logOut: ${it.message}")
                        })
                .autoDisposable()
    }

    fun logOutClick() = logOutClickSubject.onNext(Unit)

    private fun logOut() {
        router.exit()
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }
}