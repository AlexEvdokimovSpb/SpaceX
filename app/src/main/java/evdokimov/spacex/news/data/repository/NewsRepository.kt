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
            .map { launchEntity ->
                launchEntity.map { launchDto ->
                    newsMapper.createLaunchEntity(launchDto)
                }
            }
            .flatMapCompletable { launchEntities ->
                newsLocalDataSource.putLaunches(launchEntities)
            }

    override fun fetchUnauthorisedLaunches(): Completable = newsRemoteDataSource.fetchUnauthorisedLaunches()
            .map { launchDtos ->
                launchDtos.map { launchDto ->
                    newsMapper.createLaunchEntity(launchDto)
                }
            }
            .flatMapCompletable { launchEntities ->
                newsLocalDataSource.putLaunches(launchEntities)
            }

    override fun getLaunches(favoriteLaunches: List<FavoriteLaunch>): Flowable<List<Launch>> =
            newsLocalDataSource.getLaunches()
                    .map { launchEntities ->
                        launchEntities.map { launchEntity ->
                            newsMapper.createLaunch(
                                    launchEntity,
                                    favoriteLaunches.contains(FavoriteLaunch(launchEntity.id))
                            )
                        }
                    }

    override fun getLaunchById(
            id: String,
            isFavorite: Boolean
    ): Flowable<Launch> = newsLocalDataSource.getLaunchById(id)
            .map { launchEntity ->
                newsMapper.createLaunch(
                        launchEntity,
                        isFavorite
                )
            }

    override fun clear(): Completable = newsLocalDataSource.clear()
}






