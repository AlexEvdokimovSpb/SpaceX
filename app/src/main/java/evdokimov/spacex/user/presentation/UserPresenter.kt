package evdokimov.spacex.user.presentation

import com.github.terrakok.cicerone.Router
import evdokimov.spacex.base.BaseMvpPresenter
import javax.inject.Inject

class UserPresenter() : BaseMvpPresenter<UserView>() {

    @Inject
    lateinit var router: Router

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }
}