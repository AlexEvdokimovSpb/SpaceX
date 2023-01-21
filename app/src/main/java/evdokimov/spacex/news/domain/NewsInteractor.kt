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

    fun fetchUnauthorisedLaunches(): Completable = newsRepository.fetchUnauthorisedLaunches()

    fun getLaunches(
            favoriteLaunches: List<FavoriteLaunch>
    ): Flowable<List<Launch>> = newsRepository.getLaunches(favoriteLaunches)
            .map { launches -> newsFunctions.filteringLoadedLaunches(launches) }

    fun getLaunchById(
            id: String,
            isFavorite: Boolean
    ): Flowable<Launch> = newsRepository.getLaunchById(
            id,
            isFavorite
    )

    fun clear(): Completable = newsRepository.clear()
}