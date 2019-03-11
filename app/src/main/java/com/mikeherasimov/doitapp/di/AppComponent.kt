package com.mikeherasimov.doitapp.di

import com.mikeherasimov.doitapp.di.module.ContextModule
import com.mikeherasimov.doitapp.di.module.RepositoryModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class, ContextModule::class])
interface AppComponent {

}