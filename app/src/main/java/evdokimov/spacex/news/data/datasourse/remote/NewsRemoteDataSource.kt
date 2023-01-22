package evdokimov.spacex.news.data.datasourse.remote

import evdokimov.spacex.news.data.api.NewsApi
import evdokimov.spacex.news.data.entity.LaunchDto
import evdokimov.spacex.news.data.entity.ShortLaunchDto
import evdokimov.spacex.rx.SchedulerProviderContract
import io.reactivex.rxjava3.core.Single

class NewsRemoteDataSource(
        private val newsApi: NewsApi,
        private val scheduler: SchedulerProviderContract
) : NewsRemoteDataSourceApi {

    override fun fetchAuthorisedLaunches(): Single<List<LaunchDto>> = newsApi.fetchAuthorisedLaunches()
            .subscribeOn(scheduler.io())

    override fun fetchUnauthorisedLaunches(): Single<List<ShortLaunchDto>> = newsApi.fetchUnauthorisedLaunches()
            .subscribeOn(scheduler.io())
}