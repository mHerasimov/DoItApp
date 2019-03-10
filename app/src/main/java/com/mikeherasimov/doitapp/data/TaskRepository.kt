package com.mikeherasimov.doitapp.data

import androidx.lifecycle.LiveData
import com.mikeherasimov.doitapp.api.ApiService
import com.mikeherasimov.doitapp.db.Task
import com.mikeherasimov.doitapp.db.TaskDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class TaskRepository(
    private val apiService: ApiService,
    private val taskDao: TaskDao
) {

    fun getCachedTaks(): LiveData<List<Task>> = taskDao.getAllTasks()

    suspend fun getTasksFromApi(page: Int, sort: String): List<Task> {
        var tasks = emptyList<Task>()
        withContext(Dispatchers.IO) {
            tasks = apiService.getTasks(page, sort).await()
        }
        return tasks
    }

    suspend fun createTask(title: String, dueBy: String, priority: String) {
        withContext(Dispatchers.IO) {
            val task = apiService.createTask(title, dueBy, priority).await()
            taskDao.insert(task)
        }
    }

    fun getCachedTask(taskId: Int): LiveData<Task> = taskDao.getTask(taskId)

    suspend fun getTask(taskId: Int): Task {
        var task: Task? = null
        withContext(Dispatchers.IO) {
            task = apiService.getTask(taskId).await()
        }
        return task!!
    }

    suspend fun updateTask(taskId: Int, title: String, dueBy: String, priority: String) {
        withContext(Dispatchers.IO) {
            val task = apiService.updateTask(taskId, title, dueBy, priority).await()
            taskDao.update(task)
        }
    }

    suspend fun deleteTask(taskId: Int) {
        withContext(Dispatchers.IO) {
            apiService.deleteTask(taskId)
            taskDao.deleteTask(taskId)
        }
    }

}