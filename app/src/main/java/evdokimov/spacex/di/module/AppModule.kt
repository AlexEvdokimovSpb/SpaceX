package evdokimov.spacex.di.module

import dagger.Module
import dagger.Provides
import evdokimov.spacex.App
import evdokimov.spacex.rx.*
import javax.inject.Named

@Module
class AppModule(val app: App) {

    @Provides
    fun app(): App = app

    @Named("testScheduler")
    @Provides
    fun testScheduler(): SchedulerProviderContract = SchedulerProviderStub()

    @Named("scheduler")
    @Provides
    fun scheduler(): SchedulerProviderContract = SchedulerProvider()
}