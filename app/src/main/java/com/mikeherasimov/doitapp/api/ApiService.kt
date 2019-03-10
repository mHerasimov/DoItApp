package com.mikeherasimov.doitapp.api

import com.mikeherasimov.doitapp.db.Task
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface ApiService {

    @POST("/users")
    fun register(
        @Field("email") email: String,
        @Field("password") password: String
    ): Deferred<String>

    @POST("/auth")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Deferred<String>

    @GET("/tasks")
    fun getTasks(@Query("page") page: Int, @Query("sort") sort: String): Deferred<List<Task>>

    @POST("/tasks")
    fun createTask(
        @Field("title") title: String,
        @Field("dueBy") dueBy: String,
        @Field("priority") priority: String
    ): Deferred<Task>

    @GET("/tasks/{task}")
    fun getTask(@Path("task") taskId: Int): Deferred<Task>

    @PUT("/tasks/{task}")
    fun updateTask(
        @Path("task") taskId: Int,
        @Field("title") title: String,
        @Field("dueBy") dueBy: String,
        @Field("priority") priority: String
    ): Deferred<Task>

    @DELETE("/tasks/{task}")
    fun deleteTask(@Path("task") taskId: Int)

}