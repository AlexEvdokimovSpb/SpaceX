package evdokimov.spacex.news.domain

import evdokimov.spacex.favorites.domain.entity.FavoriteLaunch
import evdokimov.spacex.news.data.repository.NewsRepositoryApi
import evdokimov.spacex.news.domain.entity.Launch
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class NewsInteractor(
        private val newsRepository: NewsRepositoryApi,
        private val newsFunctions: NewsFunctions
) {

    fun fetchAuthorisedLaunches(): Completable = newsRepository.fetchAuthorisedLaunches()

    fun getAuthorisedLaunches(favoriteLaunches: List<FavoriteLaunch>): Single<List<Launch>> =
            newsRepository.getAuthorisedLaunches(favoriteLaunches)
                    .map { launches -> newsFunctions.filteringLoadedLaunches(launches) }
}