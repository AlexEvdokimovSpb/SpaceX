package evdokimov.spacex.di.module

import com.github.terrakok.cicerone.*
import dagger.Module
import dagger.Provides
import evdokimov.spacex.navigation.IScreens
import evdokimov.spacex.navigation.Screens
import javax.inject.Singleton

@Module
class CiceroneModule {

    val cicerone: Cicerone<Router> = Cicerone.create()

    @Provides
    fun navigatorHolder(): NavigatorHolder = cicerone.getNavigatorHolder()

    @Provides
    fun router(): Router = cicerone.router

    @Singleton
    @Provides
    fun screens(): IScreens = Screens()
}