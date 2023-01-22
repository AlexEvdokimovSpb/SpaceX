package evdokimov.spacex.di.module

import dagger.Module
import dagger.Provides
import evdokimov.spacex.App

@Module
class AppModule(val app: App) {

    @Provides
    fun app(): App = app
}