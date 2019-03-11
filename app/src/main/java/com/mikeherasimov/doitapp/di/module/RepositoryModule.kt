package com.mikeherasimov.doitapp.di.module

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mikeherasimov.doitapp.BuildConfig
import com.mikeherasimov.doitapp.api.ApiService
import com.mikeherasimov.doitapp.data.TaskRepository
import com.mikeherasimov.doitapp.data.UserRepository
import com.mikeherasimov.doitapp.db.AppDatabase
import com.mikeherasimov.doitapp.db.TaskDao
import com.mikeherasimov.doitapp.db.UserDao
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ContextModule::class])
class RepositoryModule {

    @Provides
    @Singleton
    fun getDb(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "db")
            .build()
    }

    @Provides
    @Singleton
    fun getUserDao(appDatabase: AppDatabase): UserDao = appDatabase.userDao()

    @Provides
    @Singleton
    fun getTaskDao(appDatabase: AppDatabase): TaskDao = appDatabase.taskDao()

    @Provides
    @Singleton
    fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
            .build()
    }

    @Provides
    @Singleton
    fun getApiService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun getTaskRepository(apiService: ApiService, taskDao: TaskDao): TaskRepository {
        return TaskRepository(apiService, taskDao)
    }

    @Provides
    @Singleton
    fun getUserRepository(apiService: ApiService, userDao: UserDao, taskDao: TaskDao): UserRepository {
        return UserRepository(apiService, userDao, taskDao)
    }

}