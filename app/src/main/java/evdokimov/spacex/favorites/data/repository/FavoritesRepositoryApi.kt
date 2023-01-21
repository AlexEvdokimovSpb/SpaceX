package evdokimov.spacex.favorites.data.repository

import evdokimov.spacex.favorites.domain.entity.FavoriteLaunch
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

interface FavoritesRepositoryApi {

    fun getAll(): Flowable<List<FavoriteLaunch>>

    fun delete(id: String): Completable

    fun insert(favoriteProduct: FavoriteLaunch): Completable

    fun clear(): Completable
}