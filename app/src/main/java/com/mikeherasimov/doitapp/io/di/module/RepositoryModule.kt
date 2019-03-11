package com.mikeherasimov.doitapp.io.di.module

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mikeherasimov.doitapp.BuildConfig
import com.mikeherasimov.doitapp.io.api.ApiService
import com.mikeherasimov.doitapp.io.data.TaskRepository
import com.mikeherasimov.doitapp.io.data.UserRepository
import com.mikeherasimov.doitapp.io.db.AppDatabase
import com.mikeherasimov.doitapp.io.db.TaskDao
import com.mikeherasimov.doitapp.io.db.UserDao
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
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
    fun getOkHttpClient(userDao: UserDao): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
            .addInterceptor { chain ->
                userDao.getUser().observeForever {
                    addToken(chain, it.token)
                }
                addToken(chain, "")
            }
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

    private fun addToken(chain: Interceptor.Chain, token: String): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader("bearerAuth", token)
            .build()
        return chain.proceed(newRequest)
    }

}