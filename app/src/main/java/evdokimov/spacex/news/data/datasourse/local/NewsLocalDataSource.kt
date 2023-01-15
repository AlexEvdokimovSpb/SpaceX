package evdokimov.spacex.news.data.datasourse.local

import evdokimov.spacex.room.NewsDao
import evdokimov.spacex.room.entity.LaunchEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class NewsLocalDataSource(
    private val newsDao: NewsDao,
) : NewsLocalDataSourceApi {

    override fun putLaunches(launches: List<LaunchEntity>): Completable =
        newsDao.insert(launches).subscribeOn(Schedulers.computation())

    override fun getLaunches(): Single<List<LaunchEntity>> = newsDao.getAll().subscribeOn(Schedulers.computation())
}

