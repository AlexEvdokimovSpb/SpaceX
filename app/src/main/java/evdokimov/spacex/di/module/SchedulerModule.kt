package evdokimov.spacex.di.module

import dagger.Module
import dagger.Provides
import evdokimov.spacex.rx.*
import javax.inject.Named

@Module
class SchedulerModule {

    @Named("testScheduler")
    @Provides
    fun testScheduler(): SchedulerProviderContract = SchedulerProviderStub()

    @Named("scheduler")
    @Provides
    fun scheduler(): SchedulerProviderContract = SchedulerProvider()
}