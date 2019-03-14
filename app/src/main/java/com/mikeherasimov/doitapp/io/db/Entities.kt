package com.mikeherasimov.doitapp.io.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class User(
    @PrimaryKey @field:SerializedName("email") val email: String,
    @field:SerializedName("password") val password: String,
    val token: String = ""
): Parcelable

@Parcelize
@Entity
data class Task(
    @PrimaryKey @field:SerializedName("id") val id: String = "",
    @field:SerializedName("title") val title: String,
    @field:SerializedName("dueBy") val dueBy: String,
    @field:SerializedName("priority") val priority: String
): Parcelable