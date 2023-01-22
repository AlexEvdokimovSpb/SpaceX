package evdokimov.spacex.di.module

import dagger.Module
import dagger.Provides
import evdokimov.spacex.di.ProdScheduler
import evdokimov.spacex.di.TestScheduler
import evdokimov.spacex.rx.*

@Module
class SchedulerModule {

    @TestScheduler
    @Provides
    fun testScheduler(): SchedulerProviderContract = SchedulerProviderStub()

    @ProdScheduler
    @Provides
    fun scheduler(): SchedulerProviderContract = SchedulerProvider()
}