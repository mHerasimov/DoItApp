package com.mikeherasimov.doitapp.io.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class, Task::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun taskDao(): TaskDao

}