package evdokimov.spacex.favorites.data.local

import evdokimov.spacex.room.FavoriteDao
import evdokimov.spacex.room.entity.FavoriteLaunchEntity
import evdokimov.spacex.rx.SchedulerProviderContract
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

class FavoritesLocalDataSource(
        private val favoriteDao: FavoriteDao,
        private val scheduler: SchedulerProviderContract
) : FavoritesLocalDataSourceApi {

    override fun getAll(): Flowable<List<FavoriteLaunchEntity>> = favoriteDao.getAll()
            .subscribeOn(scheduler.computation())

    override fun delete(id: String): Completable = favoriteDao.delete(id)
            .subscribeOn(scheduler.computation())

    override fun insert(
            favoriteProduct: FavoriteLaunchEntity
    ): Completable = favoriteDao.insert(favoriteProduct)
            .subscribeOn(scheduler.computation())

    override fun clear(): Completable = favoriteDao.clear()
            .subscribeOn(scheduler.computation())
}

