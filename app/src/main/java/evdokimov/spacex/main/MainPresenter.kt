package evdokimov.spacex.main

import com.github.terrakok.cicerone.Router
import evdokimov.spacex.navigation.IScreens
import moxy.MvpPresenter
import javax.inject.Inject

class MainPresenter : MvpPresenter<MainView>() {

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
