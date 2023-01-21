package evdokimov.spacex.favorites.domain

import evdokimov.spacex.favorites.data.repository.FavoritesRepositoryApi
import evdokimov.spacex.favorites.domain.entity.FavoriteLaunch
import evdokimov.spacex.news.domain.entity.Launch
import io.reactivex.rxjava3.core.*

class FavoritesInteractor(
        private val favoritesRepository: FavoritesRepositoryApi
) {

    fun getAll(): Flowable<List<FavoriteLaunch>> = favoritesRepository.getAll()

    fun delete(id: String): Completable = favoritesRepository.delete(id)

    fun insert(favoriteProduct: FavoriteLaunch): Completable = favoritesRepository.insert(favoriteProduct)

    fun clear(): Completable = favoritesRepository.clear()

    // Можно ничего не возвращать
    fun onFavoriteIconClick(launch: Launch): Single<Launch> = if (launch.isFavorite) {
        delete(launch.id).andThen(Single.just(launch.copy(isFavorite = false)))
    } else {
        insert(FavoriteLaunch(favoriteId = launch.id)).andThen(Single.just(launch.copy(isFavorite = true)))
    }
}