package evdokimov.spacex.authorisation.presentation

import com.github.terrakok.cicerone.Router
import evdokimov.spacex.base.BaseMvpPresenter
import evdokimov.spacex.navigation.IScreens
import evdokimov.spacex.user.domain.UserInteractor
import io.reactivex.rxjava3.core.Scheduler
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

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }
}