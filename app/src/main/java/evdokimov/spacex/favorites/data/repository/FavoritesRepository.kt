package evdokimov.spacex.favorites.data.repository

import evdokimov.spacex.favorites.data.local.FavoritesLocalDataSourceApi
import evdokimov.spacex.favorites.domain.entity.FavoriteLaunch
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

class FavoritesRepository(
        private val favoritesLocalDataSource: FavoritesLocalDataSourceApi,
        private val favoritesMapper: FavoritesMapper
) : FavoritesRepositoryApi {

    override fun getAll(): Flowable<List<FavoriteLaunch>> = favoritesLocalDataSource.getAll()
            .map { favorites -> favorites.map { favoriteEntity -> favoritesMapper.createFavoriteLaunch(favoriteEntity) } }

    override fun delete(id: String): Completable = favoritesLocalDataSource.delete(id)

    override fun insert(
            favoriteProduct: FavoriteLaunch
    ): Completable = favoritesLocalDataSource.insert(favoritesMapper.createFavoriteLaunchEntity(favoriteProduct))

    override fun clear(): Completable = favoritesLocalDataSource.clear()
}






