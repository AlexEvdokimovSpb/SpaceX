package evdokimov.spacex.di.module

import dagger.Module
import dagger.Provides
import evdokimov.spacex.di.ProdScheduler
import evdokimov.spacex.rx.SchedulerProviderContract
import evdokimov.spacex.user.data.api.UserApi
import evdokimov.spacex.user.data.datasourse.local.UserLocalDataSourceApi
import evdokimov.spacex.user.data.datasourse.remote.UserRemoteDataSource
import evdokimov.spacex.user.data.datasourse.remote.UserRemoteDataSourceApi
import evdokimov.spacex.user.data.repository.*
import evdokimov.spacex.user.domain.UserInteractor
import javax.inject.Named
import javax.inject.Singleton

@Module
class UserModule {

    @Singleton
    @Provides
    fun userInteractor(
            userRepository: UserRepositoryApi
    ): UserInteractor = UserInteractor(userRepository)

    @Singleton
    @Provides
    fun userRepository(
            userRemoteDataSource: UserRemoteDataSourceApi,
            userLocalDataSource: UserLocalDataSourceApi,
            userMapper: UserMapper
    ): UserRepositoryApi = UserRepository(
            userRemoteDataSource,
            userLocalDataSource,
            userMapper
    )

    @Singleton
    @Provides
    fun userRemoteData(
            @Named("testUserApi") userApi: UserApi,
            @ProdScheduler scheduler: SchedulerProviderContract
    ): UserRemoteDataSourceApi = UserRemoteDataSource(
            userApi,
            scheduler
    )

    @Singleton
    @Provides
    fun userMapper(): UserMapper = UserMapper()
}