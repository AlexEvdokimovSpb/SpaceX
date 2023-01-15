package evdokimov.spacex.details.presentation

import com.github.terrakok.cicerone.Router
import evdokimov.spacex.base.BaseMvpPresenter
import evdokimov.spacex.news.domain.entity.Launch
import javax.inject.Inject

private const val NUMBER_CHAR_TO_DELETE = 8

class DetailsPresenter(val launch: Launch) : BaseMvpPresenter<DetailsView>() {

    @Inject
    lateinit var router: Router

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        launch.name?.let { viewState.setName(it) }
        launch.links?.flickr?.original?.let {
            if (it.isNotEmpty()) {
                viewState.loadImage(it)
            }
        }
        launch.dateUtc?.let {
            viewState.setDate(
                it.dropLast(NUMBER_CHAR_TO_DELETE).replace('-', ' ').replace('T', ' ')
            )
        }
        launch.details?.let { viewState.setDetails(it) }
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }
}