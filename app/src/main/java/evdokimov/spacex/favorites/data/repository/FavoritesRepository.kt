package evdokimov.spacex.favorites.data.repository

import evdokimov.spacex.favorites.data.local.FavoritesLocalDataSourceApi

class FavoritesRepository(
    private val favoritesLocalDataSource: FavoritesLocalDataSourceApi, private val favoritesMapper: FavoritesMapper
) : FavoritesRepositoryApi {

}






