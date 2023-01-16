package evdokimov.spacex.authorisation.presentation

import com.github.terrakok.cicerone.Router
import evdokimov.spacex.base.BaseMvpPresenter
import evdokimov.spacex.navigation.IScreens
import evdokimov.spacex.user.domain.UserInteractor
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Named

class AuthorisationPresenter() : BaseMvpPresenter<AuthorisationView>() {

    @Inject
    @field:Named("uiScheduler")
    lateinit var uiScheduler: Scheduler

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screens: IScreens

    @Inject
    lateinit var userInteractor: UserInteractor

    private val confirmationClickSubject = PublishSubject.create<Unit>()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        confirmationClickSubject.toFlowable(BackpressureStrategy.LATEST).subscribeOn(Schedulers.computation())
            .observeOn(uiScheduler).subscribe({ confirmation() }, {
                println("Error confirmation: ${it.message}")
            }).autoDisposable()
    }

    fun confirmationClick() = confirmationClickSubject.onNext(Unit)

    private fun confirmation() {
        router.navigateTo(screens.profile())
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }
}