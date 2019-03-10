package com.mikeherasimov.doitapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val email: String,
    val password: String
)

@Entity
data class Task(
    @PrimaryKey val id: String,
    val title: String,
    val dueBy: String,
    val priority: String
)