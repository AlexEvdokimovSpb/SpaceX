package evdokimov.spacex.di.module

import androidx.room.Room
import dagger.Module
import dagger.Provides
import evdokimov.spacex.App
import evdokimov.spacex.mvp.model.cache.ILaunchesCache
import evdokimov.spacex.mvp.model.cache.LaunchesCache
import evdokimov.spacex.mvp.model.room.Database
import javax.inject.Singleton

@Module
class CacheModule {

    @Singleton
    @Provides
    fun database(app: App) = Room.databaseBuilder(app, Database::class.java, Database.DB_NAME)
        .build()

    @Singleton
    @Provides
    fun nasaApodsCache(db: Database): ILaunchesCache = LaunchesCache(db)

}