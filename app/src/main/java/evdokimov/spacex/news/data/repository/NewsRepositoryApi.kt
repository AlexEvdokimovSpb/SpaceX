package evdokimov.spacex.news.data.repository

import evdokimov.spacex.favorites.domain.entity.FavoriteLaunch
import evdokimov.spacex.news.data.entity.ShortLaunchDto
import evdokimov.spacex.news.domain.entity.Launch
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface NewsRepositoryApi {

    fun fetchAuthorisedLaunches() : Completable

    fun getAuthorisedLaunches(favoriteLaunches: List<FavoriteLaunch>): Single<List<Launch>>

    fun getUnauthorisedLaunches(): Single<List<ShortLaunchDto>>
}