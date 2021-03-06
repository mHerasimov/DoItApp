package com.mikeherasimov.doitapp.io.di

import com.mikeherasimov.doitapp.io.di.module.ContextModule
import com.mikeherasimov.doitapp.io.di.module.RepositoryModule
import com.mikeherasimov.doitapp.ui.addedittask.AddEditTaskFragment
import com.mikeherasimov.doitapp.ui.mytasks.MyTasksFragment
import com.mikeherasimov.doitapp.ui.signin.SignInActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class, ContextModule::class])
interface AppComponent {

    fun inject(fragment: MyTasksFragment)
    fun inject(activity: SignInActivity)
    fun inject(fragment: AddEditTaskFragment)

}