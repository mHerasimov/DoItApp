package com.mikeherasimov.doitapp.io.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User(
    @PrimaryKey @field:SerializedName("email") val email: String,
    @field:SerializedName("password") val password: String,
    val token: String = ""
)

@Entity
data class Task(
    @PrimaryKey @field:SerializedName("id") val id: String = "",
    @field:SerializedName("title") val title: String,
    @field:SerializedName("dueBy") val dueBy: String,
    @field:SerializedName("priority") val priority: String
)