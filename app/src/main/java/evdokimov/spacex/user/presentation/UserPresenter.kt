package evdokimov.spacex.user.presentation

import com.github.terrakok.cicerone.Router
import evdokimov.spacex.base.BaseMvpPresenter
import evdokimov.spacex.navigation.IScreens
import evdokimov.spacex.user.domain.UserInteractor
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Named

class UserPresenter() : BaseMvpPresenter<UserView>() {

    @Inject
    @field:Named("uiScheduler")
    lateinit var uiScheduler: Scheduler

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screens: IScreens

    @Inject
    lateinit var userInteractor: UserInteractor

    private val logOutClickSubject = PublishSubject.create<Unit>()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        userInteractor.getUser()
                .toFlowable()
                .subscribeOn(Schedulers.computation())
                .observeOn(uiScheduler)
                .subscribe(viewState::setUser) {
                    println("Error getUser: ${it.message}")
                }
                .autoDisposable()

        logOutClickSubject.toFlowable(BackpressureStrategy.LATEST)
                .flatMapSingle {
                    userInteractor.deleteUser()
                            .andThen(Single.just(Unit))
                }
                .subscribeOn(Schedulers.computation())
                .observeOn(uiScheduler)
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