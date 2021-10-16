package evdokimov.spacex.di.module

import dagger.Module
import dagger.Provides
import evdokimov.spacex.mvp.model.INetworkStatus
import evdokimov.spacex.mvp.model.api.IDataSource
import evdokimov.spacex.mvp.model.cache.ILaunchesCache
import evdokimov.spacex.mvp.model.repo.ISpaceXRepo
import evdokimov.spacex.mvp.model.repo.RetrofitSpaceXRepo
import javax.inject.Singleton

@Module
class RepoModule {

    @Singleton
    @Provides
    fun nasaApodsRepo(
        api: IDataSource,
        networkStatus: INetworkStatus,
        cache: ILaunchesCache,
    ): ISpaceXRepo = RetrofitSpaceXRepo(api, networkStatus, cache)
}