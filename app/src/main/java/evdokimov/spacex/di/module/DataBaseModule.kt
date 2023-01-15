package evdokimov.spacex.di.module

import androidx.room.Room
import dagger.Module
import dagger.Provides
import evdokimov.spacex.App
import evdokimov.spacex.favorites.data.local.FavoritesLocalDataSource
import evdokimov.spacex.favorites.data.local.FavoritesLocalDataSourceApi
import evdokimov.spacex.news.data.datasourse.local.NewsLocalDataSource
import evdokimov.spacex.news.data.datasourse.local.NewsLocalDataSourceApi
import evdokimov.spacex.room.Database
import evdokimov.spacex.user.data.datasourse.local.UserLocalDataSource
import evdokimov.spacex.user.data.datasourse.local.UserLocalDataSourceApi
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Singleton
    @Provides
    fun database(app: App) = Room.databaseBuilder(app, Database::class.java, Database.DB_NAME).build()

    @Singleton
    @Provides
    fun newsLocal(db: Database): NewsLocalDataSourceApi = NewsLocalDataSource(db.newsDao())

    @Singleton
    @Provides
    fun favoritesLocal(db: Database): FavoritesLocalDataSourceApi = FavoritesLocalDataSource(db.favoriteDao())

    @Singleton
    @Provides
    fun userLocal(db: Database): UserLocalDataSourceApi = UserLocalDataSource(db.userDao())
}