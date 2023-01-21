package evdokimov.spacex.favorites.data.local

import evdokimov.spacex.room.FavoriteDao
import evdokimov.spacex.room.entity.FavoriteLaunchEntity
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.schedulers.Schedulers

class FavoritesLocalDataSource(
        private val favoriteDao: FavoriteDao,
) : FavoritesLocalDataSourceApi {

    override fun getAll(): Flowable<List<FavoriteLaunchEntity>> = favoriteDao.getAll()
            .subscribeOn(Schedulers.computation())

    override fun delete(id: String): Completable = favoriteDao.delete(id)
            .subscribeOn(Schedulers.computation())

    override fun insert(
            favoriteProduct: FavoriteLaunchEntity
    ): Completable = favoriteDao.insert(favoriteProduct)
            .subscribeOn(Schedulers.computation())

    override fun clear(): Completable = favoriteDao.clear()
            .subscribeOn(Schedulers.computation())
}

