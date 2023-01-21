package evdokimov.spacex.news.domain

import evdokimov.spacex.favorites.domain.entity.FavoriteLaunch
import evdokimov.spacex.news.data.repository.NewsRepositoryApi
import evdokimov.spacex.news.domain.entity.Launch
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

class NewsInteractor(
        private val newsRepository: NewsRepositoryApi,
        private val newsFunctions: NewsFunctions
) {

    fun fetchAuthorisedLaunches(): Completable = newsRepository.fetchAuthorisedLaunches()

    fun getAuthorisedLaunches(favoriteLaunches: List<FavoriteLaunch>): Flowable<List<Launch>> =
            newsRepository.getAuthorisedLaunches(favoriteLaunches)
                    .map { launches -> newsFunctions.filteringLoadedLaunches(launches) }

    fun getUnauthorisedLaunches(): Flowable<List<Launch>> = newsRepository.getUnauthorisedLaunches()
            .map { launches -> newsFunctions.filteringLoadedLaunches(launches) }

    fun clear(): Completable = newsRepository.clear()
}