package evdokimov.spacex.mvp.presenter

import com.github.terrakok.cicerone.Router
import evdokimov.spacex.mvp.model.entity.Launch
import evdokimov.spacex.mvp.view.IViewFragmentView
import moxy.MvpPresenter
import javax.inject.Inject

private const val NUMBER_CHAR_TO_DELETE = 8

class ViewPresenter(val launch: Launch) :
    MvpPresenter<IViewFragmentView>() {

    @Inject
    lateinit var router: Router

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        launch.name?.let { viewState.setName(it) }
        launch.links?.flickr?.original?.let {
            if (it.isNotEmpty()) {
                viewState.loadImage(it[0]) // We output only the first photo
            }
        }
        launch.date_utc?.let {
            viewState.setDate(it.dropLast(NUMBER_CHAR_TO_DELETE).replace('-', ' ')
                .replace('T', ' '))
        }
        launch.details?.let { viewState.setDetails(it) }
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }
}