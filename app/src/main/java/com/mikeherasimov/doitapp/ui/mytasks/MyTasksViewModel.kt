package com.mikeherasimov.doitapp.ui.mytasks

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mikeherasimov.doitapp.io.data.TaskRepository
import com.mikeherasimov.doitapp.io.data.UserRepository
import com.mikeherasimov.doitapp.ui.base.ScopedViewModel

class MyTasksViewModel(
    private val userRepository: UserRepository,
    private val taskRepository: TaskRepository
) : ScopedViewModel() {

    val isUserLoggedIn = ObservableBoolean(true)

    init {
        userRepository.getUser().observeForever {
            isUserLoggedIn.set(it != null)
        }
    }

    class Factory(private val userRepository: UserRepository,
                  private val taskRepository: TaskRepository
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return MyTasksViewModel(userRepository, taskRepository) as T
        }

    }

}