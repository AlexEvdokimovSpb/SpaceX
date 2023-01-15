package evdokimov.spacex.di

import dagger.Component
import evdokimov.spacex.MainActivity
import evdokimov.spacex.MainPresenter
import evdokimov.spacex.details.presentation.DetailsPresenter
import evdokimov.spacex.di.module.*
import evdokimov.spacex.news.presentation.NewsPresenter
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class, CiceroneModule::class, ApiModule::class, NewsModule::class, DataBaseModule::class, UserModule::class]
)

interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(newsPresenter: NewsPresenter)
    fun inject(detailsPresenter: DetailsPresenter)
}