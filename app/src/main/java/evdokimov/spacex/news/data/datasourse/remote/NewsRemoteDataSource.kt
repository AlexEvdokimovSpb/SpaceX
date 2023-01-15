package evdokimov.spacex.news.data.datasourse.remote

import evdokimov.spacex.news.data.api.NewsApi
import evdokimov.spacex.news.data.entity.LaunchDto
import evdokimov.spacex.news.data.entity.ShortLaunchDto
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class NewsRemoteDataSource(
    private val newsApi: NewsApi
) : NewsRemoteDataSourceApi {

    override fun fetchAuthorisedLaunches(): Single<List<LaunchDto>> = newsApi.fetchAuthorisedLaunches()
        .subscribeOn(Schedulers.io())

    override fun fetchUnauthorisedLaunches(): Single<List<ShortLaunchDto>> = newsApi.fetchUnauthorisedLaunches()
        .subscribeOn(Schedulers.io())
}