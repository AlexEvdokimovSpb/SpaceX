package evdokimov.spacex.news.data.api

import evdokimov.spacex.news.data.entity.LaunchDto
import evdokimov.spacex.news.data.entity.ShortLaunchDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface NewsApi {

    @GET("v4/launches")
    fun fetchAuthorisedLaunches(): Single<List<LaunchDto>>

    @GET("v4/launches")
    fun fetchUnauthorisedLaunches(): Single<List<ShortLaunchDto>>
}