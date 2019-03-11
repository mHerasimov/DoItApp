package com.mikeherasimov.doitapp

import android.app.Application
import com.mikeherasimov.doitapp.io.di.AppComponent
import com.mikeherasimov.doitapp.io.di.DaggerAppComponent
import com.mikeherasimov.doitapp.io.di.module.ContextModule

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
            .contextModule(ContextModule(this))
            .build()
    }

    companion object {
        lateinit var component: AppComponent
            private set
    }

}