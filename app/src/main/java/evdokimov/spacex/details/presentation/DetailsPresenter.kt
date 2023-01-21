package evdokimov.spacex.details.presentation

import com.github.terrakok.cicerone.Router
import evdokimov.spacex.base.BaseMvpPresenter
import evdokimov.spacex.favorites.domain.FavoritesInteractor
import evdokimov.spacex.favorites.domain.entity.FavoriteLaunch
import evdokimov.spacex.navigation.IScreens
import evdokimov.spacex.news.domain.NewsInteractor
import evdokimov.spacex.news.domain.entity.Launch
import evdokimov.spacex.rx.toBehaviorFlowable
import evdokimov.spacex.user.domain.UserInteractor
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

private const val NUMBER_CHAR_TO_DELETE = 8

class DetailsPresenter(val id: String) : BaseMvpPresenter<DetailsView>() {

    @Inject
    @field:Named("uiScheduler")
    lateinit var uiScheduler: Scheduler

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screens: IScreens

    @Inject
    lateinit var newsInteractor: NewsInteractor

    @Inject
    lateinit var userInteractor: UserInteractor

    @Inject
    lateinit var favoritesInteractor: FavoritesInteractor

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        val isUserExists = userInteractor.isUserExists.toBehaviorFlowable()

        val favoritesFlowable = favoritesInteractor.getAll()
                .toBehaviorFlowable()

        favoritesFlowable.map { it.contains(FavoriteLaunch(id)) }
                .flatMap { isFavorite ->
                    newsInteractor.getLaunchById(
                            id,
                            isFavorite
                    )
                }
                .subscribeOn(Schedulers.computation())
                .observeOn(uiScheduler)
                .subscribe(::setScreen) {
                    println("Error setDate: ${it.message}")
                }
                .autoDisposable()
    }

    private fun setScreen(launch: Launch) {
        launch.name?.let { viewState.setName(it) }
        launch.links?.flickr?.original?.let {
            if (it.isNotEmpty()) {
                viewState.loadImage(it)
            }
        }
        launch.dateUtc?.let {
            viewState.setDate(
                    it.dropLast(NUMBER_CHAR_TO_DELETE)
                            .replace(
                                    '-',
                                    ' '
                            )
                            .replace(
                                    'T',
                                    ' '
                            )
            )
        }
        launch.details?.let { viewState.setDetails(it) }
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }
}