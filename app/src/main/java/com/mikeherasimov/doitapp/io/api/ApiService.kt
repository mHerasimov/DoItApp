package com.mikeherasimov.doitapp.io.api

import com.mikeherasimov.doitapp.io.db.Task
import com.mikeherasimov.doitapp.io.db.User
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface ApiService {

    @POST("users")
    fun registerAsync(@Body user: User): Deferred<Map<String, String>>

    @POST("auth")
    fun loginAsync(@Body user: User): Deferred<Map<String, String>>

    @GET("tasks")
    fun getTasksAsync(@Query("page") page: Int, @Query("sort") sort: String): Deferred<TasksPage>

    @POST("tasks")
    fun createTaskAsync(@Body task: Task): Deferred<Map<String, Task>>

    @GET("tasks/{task}")
    fun getTaskAsync(@Path("task") taskId: Int): Deferred<Map<String, Task>>

    @PUT("tasks/{task}")
    fun updateTaskAsync(@Path("task") taskId: Int, @Body task: Task): Deferred<List<Task>>

    @DELETE("tasks/{task}")
    fun deleteTaskAsync(@Path("task") taskId: Int): Deferred<Unit>

}