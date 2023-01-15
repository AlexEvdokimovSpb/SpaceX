package evdokimov.spacex

import android.app.Application

import evdokimov.spacex.di.AppComponent
import evdokimov.spacex.di.DaggerAppComponent
import evdokimov.spacex.di.module.AppModule
import evdokimov.spacex.room.Database

class App : Application()  {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        Database.create(this)
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    companion object {
        lateinit var instance: App
    }
}