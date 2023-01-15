package evdokimov.spacex.news.presentation

import com.github.terrakok.cicerone.Router
import evdokimov.spacex.base.BaseMvpPresenter
import evdokimov.spacex.navigation.IScreens
import evdokimov.spacex.news.domain.NewsInteractor
import evdokimov.spacex.news.domain.entity.Launch
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Named

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

    private val newsSelectSubject = PublishSubject.create<Launch>()
    private val actionUpdateSubject = PublishSubject.create<Unit>()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        newsInteractor.fetchAuthorisedLaunches().subscribeOn(Schedulers.io()).observeOn(uiScheduler)
            .subscribe({/* no-op */
                update()
            }, {
                println("Error fetchAuthorisedLaunches: ${it.message}")
            }).autoDisposable()

        actionUpdateSubject.toFlowable(BackpressureStrategy.LATEST).flatMapSingle {
            newsInteractor.getAuthorisedLaunches()
        }.subscribeOn(Schedulers.computation()).observeOn(uiScheduler).subscribe(viewState::updateView) {
            println("Error getAuthorisedLaunches: ${it.message}")
        }.autoDisposable()

        newsSelectSubject.toFlowable(BackpressureStrategy.LATEST).subscribeOn(Schedulers.computation())
            .observeOn(uiScheduler).subscribe(::newsSelect) {
                println("Error newsSelect: ${it.message}")
            }.autoDisposable()
    }

    fun onNewsSelect(launch: Launch) = newsSelectSubject.onNext(launch)

    fun update() = actionUpdateSubject.onNext(Unit)

    private fun newsSelect(launch: Launch) {
        router.navigateTo(screens.launch(launch))
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }
}