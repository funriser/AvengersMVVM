package com.funrisestudio.avengers

import android.app.Application
import com.funrisestudio.avengers.core.di.AndroidModule
import com.funrisestudio.avengers.core.di.AppComponent
import com.funrisestudio.avengers.core.di.DaggerAppComponent

class App : Application () {

    companion object {

        lateinit var appComponent: AppComponent

    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
                .builder()
                .androidModule(AndroidModule(this))
                .build()

    }

}