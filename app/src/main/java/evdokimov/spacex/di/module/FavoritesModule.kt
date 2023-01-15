package evdokimov.spacex.di.module

import dagger.Module
import dagger.Provides
import evdokimov.spacex.favorites.data.local.FavoritesLocalDataSourceApi
import evdokimov.spacex.favorites.data.repository.*
import evdokimov.spacex.favorites.domain.FavoritesInteractor
import javax.inject.Singleton

@Module
class FavoritesModule {

    @Singleton
    @Provides
    fun favoritesInteractor(
        favoritesRepository: FavoritesRepositoryApi
    ): FavoritesInteractor = FavoritesInteractor(favoritesRepository)

    @Singleton
    @Provides
    fun favoritesRepository(
        favoritesLocalDataSource: FavoritesLocalDataSourceApi, favoritesMapper: FavoritesMapper
    ): FavoritesRepositoryApi = FavoritesRepository(favoritesLocalDataSource, favoritesMapper)

    @Singleton
    @Provides
    fun favoritesMapper(): FavoritesMapper = FavoritesMapper()
}