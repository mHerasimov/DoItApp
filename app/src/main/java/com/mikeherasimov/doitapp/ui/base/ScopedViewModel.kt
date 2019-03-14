package com.mikeherasimov.doitapp.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class ScopedViewModel: ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    protected val _networkError = MutableLiveData<Boolean>(false)
    val networkError: LiveData<Boolean>
        get() = _networkError

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}