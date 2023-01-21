package evdokimov.spacex.favorites.data.local

import evdokimov.spacex.room.entity.FavoriteLaunchEntity
import io.reactivex.rxjava3.core.*

interface FavoritesLocalDataSourceApi {

    fun getAll(): Flowable<List<FavoriteLaunchEntity>>

    fun delete(id: String): Completable

    fun insert(favoriteProduct: FavoriteLaunchEntity): Completable

    fun clear(): Completable
}