package evdokimov.spacex.news.data.datasourse.remote

import evdokimov.spacex.news.data.entity.LaunchDto
import evdokimov.spacex.news.data.entity.ShortLaunchDto
import io.reactivex.rxjava3.core.Single

interface NewsRemoteDataSourceApi {

    fun fetchAuthorisedLaunches(): Single<List<LaunchDto>>

    fun fetchUnauthorisedLaunches(): Single<List<ShortLaunchDto>>
}