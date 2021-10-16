package evdokimov.spacex.mvp.presenter

import com.github.terrakok.cicerone.Router
import evdokimov.spacex.mvp.model.entity.Launch
import evdokimov.spacex.mvp.model.repo.ISpaceXRepo
import evdokimov.spacex.mvp.navigation.IScreens
import evdokimov.spacex.mvp.presenter.list.ILaunchesListPresenter
import evdokimov.spacex.mvp.view.IStartFragmentView
import evdokimov.spacex.mvp.view.list.ILaunchItemView
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import javax.inject.Inject
import javax.inject.Named

private const val NUMBER_CHAR_TO_DELETE = 14

class StartPresenter :
    MvpPresenter<IStartFragmentView>() {

    @Inject
    @field:Named("uiScheduler")
    lateinit var uiScheduler: Scheduler

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screens: IScreens

    @Inject
    lateinit var retrofitSpaceXRepo: ISpaceXRepo

    class LaunchesListPresenter : ILaunchesListPresenter {
        var launches = mutableListOf<Launch>()
        override var itemClickListener: ((ILaunchItemView) -> Unit)? = null

        override fun bindView(view: ILaunchItemView) {
            val launch = launches[view.pos]
            launch.date_utc?.let {
                view.setDate(it.dropLast(NUMBER_CHAR_TO_DELETE).replace('-', ' '))
            }
            launch.name?.let { view.setTitle(it) }
        }

        override fun getCount() = launches.size

        override fun sortAscendingDateLoadedLaunches() {
            val sortedLaunches = launches.sortedWith(
                Comparator { launch1, launch2 ->
                    launch1.date_utc?.dropLast(14)?.replace('-', '0')?.toInt()?.let {
                        launch2.date_utc?.dropLast(14)?.replace('-', '0')?.toInt()
                            ?.minus(it)
                    }!!
                }
            ) as MutableList<Launch>
            launches = sortedLaunches
        }

        override fun sortDescendingDateLoadedLaunches() {
            val sortedLaunches = launches.sortedWith(
                Comparator { launch1, launch2 ->
                    launch2.date_utc?.dropLast(14)?.replace('-', '0')?.toInt()?.let {
                        launch1.date_utc?.dropLast(14)?.replace('-', '0')?.toInt()
                            ?.minus(it)
                    }!!
                }
            ) as MutableList<Launch>
            launches = sortedLaunches
        }
    }

    val launchesListPresenter = LaunchesListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()
        launchesListPresenter.itemClickListener = { view ->
            val launch = launchesListPresenter.launches[view.pos]
            router.navigateTo(screens.launch(launch))
        }
    }

    fun loadData() {
        retrofitSpaceXRepo.getLaunches()
            .observeOn(uiScheduler)
            .subscribe({ repos ->
                launchesListPresenter.launches.clear()
                launchesListPresenter.launches.addAll(repos)
                viewState.updateList()
            }, {
                println("Error: ${it.message}")
            })
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }
}