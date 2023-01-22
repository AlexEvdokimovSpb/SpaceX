package evdokimov.spacex.di.module

import dagger.Module
import dagger.Provides
import evdokimov.spacex.di.ProdScheduler
import evdokimov.spacex.news.data.api.NewsApi
import evdokimov.spacex.news.data.datasourse.local.NewsLocalDataSourceApi
import evdokimov.spacex.news.data.datasourse.remote.NewsRemoteDataSource
import evdokimov.spacex.news.data.datasourse.remote.NewsRemoteDataSourceApi
import evdokimov.spacex.news.data.repository.*
import evdokimov.spacex.news.domain.NewsFunctions
import evdokimov.spacex.news.domain.NewsInteractor
import evdokimov.spacex.rx.SchedulerProviderContract
import javax.inject.Singleton

@Module
class NewsModule {

    @Singleton
    @Provides
    fun newsInteractor(
            newsRepository: NewsRepositoryApi,
            newsFunctions: NewsFunctions
    ): NewsInteractor = NewsInteractor(
            newsRepository,
            newsFunctions
    )

    @Singleton
    @Provides
    fun newsRepository(
            newsRemoteDataSource: NewsRemoteDataSourceApi,
            newsLocalDataSource: NewsLocalDataSourceApi,
            newsMapper: NewsMapper
    ): NewsRepositoryApi = NewsRepository(
            newsRemoteDataSource,
            newsLocalDataSource,
            newsMapper
    )

    @Singleton
    @Provides
    fun newsRemoteData(
            newsApi: NewsApi,
            @ProdScheduler scheduler: SchedulerProviderContract
    ): NewsRemoteDataSourceApi = NewsRemoteDataSource(
            newsApi,
            scheduler
    )

    @Singleton
    @Provides
    fun newsMapper(): NewsMapper = NewsMapper()

    @Singleton
    @Provides
    fun newsFunctions(): NewsFunctions = NewsFunctions()
}