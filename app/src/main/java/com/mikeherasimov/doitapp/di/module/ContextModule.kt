package com.mikeherasimov.doitapp.di.module

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ContextModule(
        @get:Provides
        val context: Context
)