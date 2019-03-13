package com.mikeherasimov.doitapp.ui.addedittask

import androidx.databinding.ObservableField
import androidx.databinding.ObservableLong
import com.mikeherasimov.doitapp.ui.base.ScopedViewModel
import com.mikeherasimov.doitapp.ui.util.eraseDate
import com.mikeherasimov.doitapp.ui.util.eraseTime
import java.util.*

class AddEditTaskViewModel: ScopedViewModel() {

    val dateTimestamp = ObservableLong()
    val timeTimestamp = ObservableLong()
    val title = ObservableField<String>()

    val noTitleError = ObservableField<Int?>()

    init {
        initDate()
        initTime()
    }

    private fun initDate() {
        val calendar = Calendar.getInstance().apply { eraseTime() }
        dateTimestamp.set(calendar.timeInMillis)
    }

    private fun initTime() {
        val calendar = Calendar.getInstance().apply { eraseDate() }
        timeTimestamp.set(calendar.timeInMillis)
    }

}