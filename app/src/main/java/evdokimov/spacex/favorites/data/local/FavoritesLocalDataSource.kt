package evdokimov.spacex.favorites.data.local

import evdokimov.spacex.room.FavoriteDao
import evdokimov.spacex.room.entity.FavoriteLaunchEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class FavoritesLocalDataSource(
    private val favoriteDao: FavoriteDao,
) : FavoritesLocalDataSourceApi {

    override fun getAll(): Single<List<FavoriteLaunchEntity>> =
        favoriteDao.getAll().subscribeOn(Schedulers.computation())

    override fun findById(id: Int): Single<FavoriteLaunchEntity> =
        favoriteDao.findById(id).subscribeOn(Schedulers.computation())

    override fun deleteById(id: Int): Completable = favoriteDao.deleteById(id).subscribeOn(Schedulers.computation())

    override fun insert(vararg favoriteProduct: FavoriteLaunchEntity): Completable =
        favoriteDao.insert(*favoriteProduct).subscribeOn(Schedulers.computation())

    override fun clear(): Completable = favoriteDao.clear().subscribeOn(Schedulers.computation())
}

