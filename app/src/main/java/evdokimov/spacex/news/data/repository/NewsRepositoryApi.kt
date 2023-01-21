package evdokimov.spacex.news.data.repository

import evdokimov.spacex.favorites.domain.entity.FavoriteLaunch
import evdokimov.spacex.news.domain.entity.Launch
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

interface NewsRepositoryApi {

    fun fetchAuthorisedLaunches(): Completable

    fun fetchUnauthorisedLaunches(): Completable

    fun getLaunches(favoriteLaunches: List<FavoriteLaunch>): Flowable<List<Launch>>

    fun getLaunchById(
            id: String,
            isFavorite: Boolean
    ): Flowable<Launch>

    fun clear(): Completable
}