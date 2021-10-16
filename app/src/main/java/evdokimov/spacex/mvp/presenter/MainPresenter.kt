package evdokimov.spacex.mvp.presenter

import com.github.terrakok.cicerone.Router
import evdokimov.spacex.mvp.navigation.IScreens
import evdokimov.spacex.mvp.view.IMainView
import moxy.MvpPresenter
import javax.inject.Inject

class MainPresenter : MvpPresenter<IMainView>() {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screens: IScreens

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(screens.launches())
    }

    fun backClicked() {
        router.exit()
    }
}
