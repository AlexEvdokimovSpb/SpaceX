package evdokimov.spacex.news.data.repository

import evdokimov.spacex.favorites.domain.entity.FavoriteLaunch
import evdokimov.spacex.news.data.datasourse.local.NewsLocalDataSourceApi
import evdokimov.spacex.news.data.datasourse.remote.NewsRemoteDataSourceApi
import evdokimov.spacex.news.domain.entity.Launch
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

class NewsRepository(
        private val newsRemoteDataSource: NewsRemoteDataSourceApi,
        private val newsLocalDataSource: NewsLocalDataSourceApi,
        private val newsMapper: NewsMapper
) : NewsRepositoryApi {

    override fun fetchAuthorisedLaunches(): Completable = newsRemoteDataSource.fetchAuthorisedLaunches()
            .map { launchDtos ->
                launchDtos.map { launchDto ->
                    newsMapper.createLaunchEntity(launchDto)
                }
            }
            .flatMapCompletable { launchRoom ->
                newsLocalDataSource.putLaunches(launchRoom)
            }

    override fun getAuthorisedLaunches(favoriteLaunches: List<FavoriteLaunch>): Flowable<List<Launch>> =
            newsLocalDataSource.getLaunches()
                    .map { launchEntities ->
                        launchEntities.map { launchEntity ->
                            newsMapper.createLaunch(
                                    launchEntity,
                                    favoriteLaunches.contains(FavoriteLaunch(launchEntity.id))
                            )
                        }
                    }

    override fun getUnauthorisedLaunches(): Flowable<List<Launch>> = newsLocalDataSource.getLaunches()
            .map { launchEntities -> launchEntities.map { launchEntity -> newsMapper.createLaunch(launchEntity) } }

    override fun clear(): Completable = newsLocalDataSource.clear()
}






