package com.mikeherasimov.doitapp

import android.app.Application
import com.mikeherasimov.doitapp.di.AppComponent
import com.mikeherasimov.doitapp.di.DaggerAppComponent
import com.mikeherasimov.doitapp.di.module.ContextModule

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