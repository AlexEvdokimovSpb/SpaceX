package evdokimov.spacex.di

import dagger.Component
import evdokimov.spacex.MainActivity
import evdokimov.spacex.MainPresenter
import evdokimov.spacex.authorisation.presentation.AuthorisationPresenter
import evdokimov.spacex.details.presentation.DetailsPresenter
import evdokimov.spacex.di.module.*
import evdokimov.spacex.news.presentation.NewsPresenter
import evdokimov.spacex.user.presentation.UserPresenter
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        SchedulerModule::class,
        CiceroneModule::class,
        ApiModule::class,
        NewsModule::class,
        DataBaseModule::class,
        UserModule::class,
        FavoritesModule::class
    ]
)

interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(newsPresenter: NewsPresenter)
    fun inject(detailsPresenter: DetailsPresenter)
    fun inject(userPresenter: UserPresenter)
    fun inject(authorisationPresenter: AuthorisationPresenter)
}