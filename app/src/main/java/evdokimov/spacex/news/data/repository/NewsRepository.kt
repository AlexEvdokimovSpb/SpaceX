package evdokimov.spacex.news.data.repository

import evdokimov.spacex.news.data.datasourse.local.NewsLocalDataSourceApi
import evdokimov.spacex.news.data.datasourse.remote.NewsRemoteDataSourceApi
import evdokimov.spacex.news.data.entity.ShortLaunchDto
import evdokimov.spacex.news.domain.entity.Launch
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class NewsRepository(
    private val newsRemoteDataSource: NewsRemoteDataSourceApi,
    private val newsLocalDataSource: NewsLocalDataSourceApi,
    private val newsMapper: NewsMapper,
) : NewsRepositoryApi {

    override fun fetchAuthorisedLaunches(): Completable =
        newsRemoteDataSource.fetchAuthorisedLaunches().map { launchDtos ->
            launchDtos.map { launchDto ->
                newsMapper.createLaunchEntity(launchDto)
            }
        }.flatMapCompletable { launchRoom ->
            newsLocalDataSource.putLaunches(launchRoom)
        }

    override fun getAuthorisedLaunches(): Single<List<Launch>> = newsLocalDataSource.getLaunches().map { launchRooms ->
        launchRooms.map { launchRoom ->
            newsMapper.createLaunch(launchRoom)
        }
    }

    override fun getUnauthorisedLaunches(): Single<List<ShortLaunchDto>> {
        return newsRemoteDataSource.fetchUnauthorisedLaunches()
    }
}






