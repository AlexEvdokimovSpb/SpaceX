package evdokimov.spacex.di

import dagger.Component
import evdokimov.spacex.di.module.*
import evdokimov.spacex.mvp.presenter.MainPresenter
import evdokimov.spacex.mvp.presenter.StartPresenter
import evdokimov.spacex.mvp.presenter.ViewPresenter
import evdokimov.spacex.ui.MainActivity
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        CiceroneModule::class,
        ApiModule::class,
        RepoModule::class,
        CacheModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(startPresenter: StartPresenter)
    fun inject(viewPresenter: ViewPresenter)
}