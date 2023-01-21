package evdokimov.spacex.authorisation.presentation

import com.github.terrakok.cicerone.Router
import evdokimov.spacex.base.BaseMvpPresenter
import evdokimov.spacex.navigation.IScreens
import evdokimov.spacex.rx.withLatestFrom
import evdokimov.spacex.user.domain.UserInteractor
import evdokimov.spacex.user.domain.entity.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.kotlin.combineLatest
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

class AuthorisationPresenter : BaseMvpPresenter<AuthorisationView>() {

    companion object {

        private const val INPUT_TEXT_DELAY = 200L
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
    private val saveUserSubject = PublishSubject.create<User>()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        val loginChangedFlowable = loginChangedSubject.toFlowable(BackpressureStrategy.LATEST)
                .observeOn(Schedulers.computation())
                .distinctUntilChanged()
                .debounce(
                        INPUT_TEXT_DELAY,
                        TimeUnit.MILLISECONDS
                )

        val passwordChangedFlowable = passwordChangedSubject.toFlowable(BackpressureStrategy.LATEST)
                .observeOn(Schedulers.computation())
                .distinctUntilChanged()
                .debounce(
                        INPUT_TEXT_DELAY,
                        TimeUnit.MILLISECONDS
                )

        val loginAndPasswordFlowable = loginChangedFlowable.combineLatest(passwordChangedFlowable)

        confirmationClickSubject.toFlowable(BackpressureStrategy.LATEST)
                .withLatestFrom(loginAndPasswordFlowable)
                .map { it.second }
                .flatMapMaybe { (login, password) ->
                    userInteractor.fetchUser(
                            password,
                            login
                    )
                }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ::confirmation
                ) {
                    println("Error confirmation: ${it.message}")
                }
                .autoDisposable()

        saveUserSubject.toFlowable(BackpressureStrategy.LATEST)
                .flatMapSingle {
                    userInteractor.insertUser(it)
                            .andThen(Single.just(Unit))
                }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    router.replaceScreen(screens.profile())
                },
                        {
                            println("Error saveUser: ${it.message}")
                        })
                .autoDisposable()
    }

    fun loginChanged(login: String) = loginChangedSubject.onNext(login)

    fun passwordChanged(password: String) = passwordChangedSubject.onNext(password)

    fun confirmationClick() = confirmationClickSubject.onNext(Unit)

    private fun confirmation(user: User) = saveUserSubject.onNext(user)

    fun backClick(): Boolean {
        router.exit()
        return true
    }
}