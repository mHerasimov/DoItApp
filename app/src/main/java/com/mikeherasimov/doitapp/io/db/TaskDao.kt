package com.mikeherasimov.doitapp.io.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface TaskDao: BaseDao<Task> {

    @Query("SELECT * FROM task")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM task WHERE id = :taskId")
    fun getTask(taskId: Int): LiveData<Task>

    @Query("DELETE FROM task")
    fun deleteAll()

    @Query("DELETE FROM task WHERE id = :taskId")
    fun deleteTask(taskId: Int)

}