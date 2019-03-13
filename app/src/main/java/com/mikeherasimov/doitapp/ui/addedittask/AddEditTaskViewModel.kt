package com.mikeherasimov.doitapp.ui.addedittask

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableLong
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mikeherasimov.doitapp.R
import com.mikeherasimov.doitapp.io.data.TaskRepository
import com.mikeherasimov.doitapp.ui.base.ScopedViewModel
import kotlinx.coroutines.launch
import java.util.*

class AddEditTaskViewModel(
    private val taskRepository: TaskRepository,
    defaultPriority: String,
    private val taskId: Int = 0,
    private val isInAddMode: Boolean = true
): ScopedViewModel() {

    val title = ObservableField<String>()
    val priority = ObservableField<String>(defaultPriority)
    val deadlineTimestamp = ObservableLong()

    val noTitleError = ObservableField<Int?>()
    val onTaskSuccessfullyUpdatedOrCreated = ObservableBoolean(false)

    init {
        val calendar = Calendar.getInstance().apply {
            timeInMillis += 15 * 60 * 1000
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        deadlineTimestamp.set(calendar.timeInMillis / 1000)
    }

    fun setDeadlineDate(year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = deadlineTimestamp.get()
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DATE, day)
        }
        deadlineTimestamp.set(calendar.timeInMillis / 1000)
    }

    fun setDeadlineTime(hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = deadlineTimestamp.get()
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }
        deadlineTimestamp.set(calendar.timeInMillis / 1000)
    }

    fun validate(): Boolean {
        if (title.get().isNullOrEmpty()) {
            noTitleError.set(R.string.error_no_title)
            return false
        } else {
            noTitleError.set(null)
        }
        // TODO check does picked deadline already passed
        return true
    }

    fun updateTask() {
        launch {
            taskRepository.updateTask(
                taskId, title.get()!!, deadlineTimestamp.get().toString(), priority.get()!!)
            onTaskSuccessfullyUpdatedOrCreated.set(true)
        }
    }

    fun createTask() {
        launch {
            Log.d("AddEditTaskViewModel", deadlineTimestamp.get().toString())
            taskRepository.createTask(
                title.get()!!, deadlineTimestamp.get().toString(), priority.get()!!)
            onTaskSuccessfullyUpdatedOrCreated.set(true)
        }
    }

    class Factory(
        private val taskRepository: TaskRepository,
        private val defaultPriority: String,
        private val taskId: Int = 0,
        private val isInAddMode: Boolean = true
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return AddEditTaskViewModel(taskRepository, defaultPriority, taskId, isInAddMode) as T
        }

    }

}