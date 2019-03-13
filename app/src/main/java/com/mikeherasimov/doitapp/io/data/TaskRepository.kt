package com.mikeherasimov.doitapp.io.data

import androidx.lifecycle.LiveData
import com.mikeherasimov.doitapp.io.api.ApiService
import com.mikeherasimov.doitapp.io.db.Task
import com.mikeherasimov.doitapp.io.db.TaskDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository(
    private val apiService: ApiService,
    private val taskDao: TaskDao
) {

    fun getCachedTasks(): LiveData<List<Task>> = taskDao.getAllTasks()

    suspend fun getTasksFromApi(page: Int, sort: String): List<Task> {
        var tasks = emptyList<Task>()
        withContext(Dispatchers.IO) {
            tasks = apiService.getTasksAsync(page, sort).await()
        }
        return tasks
    }

    suspend fun createTask(title: String, dueBy: String, priority: String) {
        withContext(Dispatchers.IO) {
            val requestTask = Task(title = title, dueBy = dueBy, priority = priority)
            val responseTask = apiService.createTaskAsync(requestTask).await().getValue("task")
            taskDao.insert(responseTask)
        }
    }

    fun getCachedTask(taskId: Int): LiveData<Task> = taskDao.getTask(taskId)

    suspend fun getTask(taskId: Int): Task {
        var task: Task? = null
        withContext(Dispatchers.IO) {
            task = apiService.getTaskAsync(taskId).await()
        }
        return task!!
    }

    suspend fun updateTask(taskId: Int, title: String, dueBy: String, priority: String) {
        withContext(Dispatchers.IO) {
            val requestTask = Task(taskId.toString(), title, dueBy, priority)
            val responseTask = apiService.updateTaskAsync(taskId, requestTask).await()
            taskDao.update(responseTask)
        }
    }

    suspend fun deleteTask(taskId: Int) {
        withContext(Dispatchers.IO) {
            apiService.deleteTask(taskId)
            taskDao.deleteTask(taskId)
        }
    }

    suspend fun saveTasksToCache(tasks: List<Task>) {
        withContext(Dispatchers.IO) {
            taskDao.insertAll(tasks)
        }
    }

}