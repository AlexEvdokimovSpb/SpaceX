package evdokimov.spacex.favorites.data.local

import evdokimov.spacex.room.entity.FavoriteLaunchEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface FavoritesLocalDataSourceApi {

    fun getAll(): Single<List<FavoriteLaunchEntity>>

    fun findById(id: Int): Single<FavoriteLaunchEntity>

    fun deleteById(id: Int): Completable

    fun insert(vararg favoriteProduct: FavoriteLaunchEntity): Completable

    fun clear(): Completable
}