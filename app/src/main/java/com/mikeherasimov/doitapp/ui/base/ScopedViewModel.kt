package com.mikeherasimov.doitapp.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class ScopedViewModel: ViewModel(), CoroutineScope {

    private val job = Job()
    protected val scope = CoroutineScope(job + Dispatchers.Main)
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}