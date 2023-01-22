package evdokimov.spacex.news.data.datasourse.local

import evdokimov.spacex.room.NewsDao
import evdokimov.spacex.room.entity.LaunchEntity
import evdokimov.spacex.rx.SchedulerProviderContract
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

class NewsLocalDataSource(
        private val newsDao: NewsDao,
        private val scheduler: SchedulerProviderContract
) : NewsLocalDataSourceApi {

    override fun putLaunches(launches: List<LaunchEntity>): Completable = newsDao.insert(launches)
            .subscribeOn(scheduler.computation())

    override fun getLaunches(): Flowable<List<LaunchEntity>> = newsDao.getAll()
            .subscribeOn(scheduler.computation())

    override fun getLaunchById(id: String): Flowable<LaunchEntity> = newsDao.get(id)
            .subscribeOn(scheduler.computation())

    override fun clear(): Completable = newsDao.clear()
            .subscribeOn(scheduler.computation())
}

