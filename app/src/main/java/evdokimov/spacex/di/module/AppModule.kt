package evdokimov.spacex.di.module

import dagger.Module
import dagger.Provides
import evdokimov.spacex.App
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Named

@Module
class AppModule(val app: App) {

    @Provides
    fun app(): App = app

    @Named("uiScheduler")
    @Provides
    fun uiScheduler(): Scheduler = AndroidSchedulers.mainThread()
}