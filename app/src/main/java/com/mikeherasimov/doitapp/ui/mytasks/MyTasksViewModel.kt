package com.mikeherasimov.doitapp.ui.mytasks

import androidx.lifecycle.*
import com.mikeherasimov.doitapp.io.data.TaskRepository
import com.mikeherasimov.doitapp.io.data.UserRepository
import com.mikeherasimov.doitapp.io.db.Task
import com.mikeherasimov.doitapp.ui.base.ScopedViewModel
import kotlinx.coroutines.launch
import java.lang.Exception

class MyTasksViewModel(
    userRepository: UserRepository,
    private val taskRepository: TaskRepository
) : ScopedViewModel() {

    private val _isUserLoggedIn = MutableLiveData<Boolean>(true)
    val isUserLoggedIn: LiveData<Boolean>
        get() = _isUserLoggedIn
    private val _networkError = MutableLiveData<Boolean>(false)
    val networkError: LiveData<Boolean>
        get() = _networkError

    private var lastRequestedPage = 1
    private var isRequestInProgress = false
    private lateinit var lastSortingOrder: String

    init {
        userRepository.getUser().observeForever {
            _isUserLoggedIn.value = it != null
        }
    }

    fun search(sortingOrder: String): LiveData<List<Task>> {
        lastRequestedPage = 1
        lastSortingOrder = sortingOrder
        requestAndSaveData(sortingOrder)
        val tasks = taskRepository.getCachedTasks()
        return Transformations.map(tasks) { list ->
            sortTasks(list, sortingOrder)
        }
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            requestMore(lastSortingOrder)
        }
    }

    private fun requestMore(sortingOrder: String) {
        requestAndSaveData(sortingOrder)
    }

    private fun requestAndSaveData(sortingOrder: String) {
        if (isRequestInProgress) {
            return
        }

        isRequestInProgress = true
        try {
            launch {
                val tasksPage = taskRepository.getTasksFromApi(lastRequestedPage, sortingOrder)
                taskRepository.saveTasksToCache(tasksPage.tasks)
                lastRequestedPage++
            }
        } catch (e: Exception) {
            isRequestInProgress = false
            _networkError.value = true
        } finally {
            isRequestInProgress = false
            _networkError.value = false
        }
    }

    private fun sortTasks(list: List<Task>, sortingOrder: String): List<Task> {
        var result: List<Task>
        val propertyToBeSorted = sortingOrder.split(" ")[0]
        val order = sortingOrder.split(" ")[1]
        result = when (propertyToBeSorted) {
            "title" -> list.sortedBy { it.title }
            "priority" -> list.sortedBy { it.priority }
            else -> list.sortedBy { it.dueBy.toLong() }
        }
        if (order.equals("desc", true)) {
            result = result.reversed()
        }
        return result
    }

    class Factory(
        private val userRepository: UserRepository,
        private val taskRepository: TaskRepository
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return MyTasksViewModel(userRepository, taskRepository) as T
        }

    }

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

}