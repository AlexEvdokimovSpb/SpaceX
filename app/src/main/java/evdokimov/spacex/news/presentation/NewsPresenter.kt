package evdokimov.spacex.news.presentation

import com.github.terrakok.cicerone.Router
import evdokimov.spacex.base.BaseMvpPresenter
import evdokimov.spacex.navigation.IScreens
import evdokimov.spacex.news.domain.NewsInteractor
import evdokimov.spacex.news.domain.entity.Launch
import evdokimov.spacex.news.presentation.list.INewsItemView
import evdokimov.spacex.news.presentation.list.INewsListPresenter
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

private const val NUMBER_CHAR_TO_DELETE = 14

class NewsPresenter : BaseMvpPresenter<NewsView>() {

    @Inject
    @field:Named("uiScheduler")
    lateinit var uiScheduler: Scheduler

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screens: IScreens

    @Inject
    lateinit var newsInteractor: NewsInteractor

    class NewsListPresenter : INewsListPresenter {

        var launches = mutableListOf<Launch>()
        override var itemClickListener: ((INewsItemView) -> Unit)? = null

        override fun bindView(view: INewsItemView) {
            val launch = launches[view.pos]
            launch.dateUtc?.let {
                view.setDate(it.dropLast(NUMBER_CHAR_TO_DELETE).replace('-', ' '))
            }
            launch.name?.let { view.setTitle(it) }
        }

        override fun getCount() = launches.size

        override fun sortAscendingDateLoadedLaunches() {
            val sortedLaunches = launches.sortedWith(Comparator { launch1, launch2 ->
                launch1.dateUtc?.dropLast(14)?.replace('-', '0')?.toInt()?.let {
                    launch2.dateUtc?.dropLast(14)?.replace('-', '0')?.toInt()?.minus(it)
                } ?: 0
            }) as MutableList<Launch>
            launches = sortedLaunches
        }

        override fun sortDescendingDateLoadedLaunches() {
            val sortedLaunches = launches.sortedWith(Comparator { launch1, launch2 ->
                launch2.dateUtc?.dropLast(14)?.replace('-', '0')?.toInt()?.let {
                    launch1.dateUtc?.dropLast(14)?.replace('-', '0')?.toInt()?.minus(it)
                } ?: 0
            }) as MutableList<Launch>
            launches = sortedLaunches
        }
    }

    val launchesListPresenter = NewsListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        newsInteractor.fetchAuthorisedLaunches().subscribeOn(Schedulers.io())
            .observeOn(uiScheduler) //            .subscribeOn(schedulers.computation)
            .subscribe({
                viewState.init()
                loadData()
            }, {
                println("Error: ${it.message}")
            }).autoDisposable()

        launchesListPresenter.itemClickListener = { view ->
            val launch = launchesListPresenter.launches[view.pos]
            router.navigateTo(screens.launch(launch))
        }
    }

    fun loadData() {
        newsInteractor.getAuthorisedLaunches().subscribeOn(Schedulers.io()).observeOn(uiScheduler).subscribe({ repos ->
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