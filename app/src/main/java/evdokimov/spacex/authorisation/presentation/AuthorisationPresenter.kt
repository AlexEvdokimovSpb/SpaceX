package evdokimov.spacex.authorisation.presentation

import com.github.terrakok.cicerone.Router
import evdokimov.spacex.base.BaseMvpPresenter
import evdokimov.spacex.navigation.IScreens
import evdokimov.spacex.rx.toBehaviorFlowable
import evdokimov.spacex.user.domain.UserInteractor
import evdokimov.spacex.user.domain.entity.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.kotlin.combineLatest
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

class AuthorisationPresenter() : BaseMvpPresenter<AuthorisationView>() {

    companion object {

        private const val INPUT_TEXT_DELAY = 200L
        const val PASSWORD = "123456"
        const val LOGIN = "login"
    }

    @Inject
    @field:Named("uiScheduler")
    lateinit var uiScheduler: Scheduler

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screens: IScreens

    @Inject
    lateinit var userInteractor: UserInteractor

    private val loginChangedSubject = PublishSubject.create<String>()
    private val passwordChangedSubject = PublishSubject.create<String>()
    private val confirmationClickSubject = PublishSubject.create<Unit>()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        val loginChangedFlowable =
            loginChangedSubject.toFlowable(BackpressureStrategy.LATEST).observeOn(Schedulers.computation())
                .distinctUntilChanged().debounce(INPUT_TEXT_DELAY, TimeUnit.MILLISECONDS).toBehaviorFlowable()

        val passwordChangedFlowable =
            passwordChangedSubject.toFlowable(BackpressureStrategy.LATEST).observeOn(Schedulers.computation())
                .distinctUntilChanged().debounce(INPUT_TEXT_DELAY, TimeUnit.MILLISECONDS).toBehaviorFlowable()

        val loginAndPasswordFlowable = loginChangedFlowable.combineLatest(passwordChangedFlowable)

        confirmationClickSubject.toFlowable(BackpressureStrategy.LATEST).flatMap { loginAndPasswordFlowable }
            .flatMap { (login, password) ->
                userInteractor.fetchUser(password, login).toFlowable()
            }.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe({
                viewState.showMessage("успех") //                confirmation(it)
            }, {
                viewState.showMessage("ошибка")
                println("Error confirmation: ${it.message}")
            }, {
                viewState.showMessage("неверный логин или пароль")
            }).autoDisposable()
    }

    fun loginChanged(login: String) = loginChangedSubject.onNext(login)

    fun passwordChanged(password: String) = passwordChangedSubject.onNext(password)

    fun confirmationClick() = confirmationClickSubject.onNext(Unit)

    private fun confirmation(user: User) {
        router.navigateTo(screens.profile())
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }
}