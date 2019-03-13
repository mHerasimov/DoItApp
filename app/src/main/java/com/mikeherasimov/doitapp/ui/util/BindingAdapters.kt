package com.mikeherasimov.doitapp.ui.util

import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.databinding.ObservableLong
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("error")
fun bindError(textInputLayout: TextInputLayout, error: ObservableField<Int?>) {
    val errorRes = error.get()
    if (errorRes != null) {
        textInputLayout.isErrorEnabled = true
        textInputLayout.error = textInputLayout.context.getString(errorRes)
    } else {
        textInputLayout.isErrorEnabled = false
        textInputLayout.error = null
    }
}

@BindingAdapter("date")
fun bindDate(editText: EditText, timestamp: ObservableLong) {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp.get()
    val format = SimpleDateFormat.getDateInstance()
    editText.setText(format.format(calendar.time))
}

@BindingAdapter("time")
fun bindTime(editText: EditText, timestamp: ObservableLong) {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp.get()
    val format = SimpleDateFormat.getTimeInstance()
    editText.setText(format.format(calendar.time))
}

@BindingAdapter("dateAndTime")
fun bindDateAndTime(textView: TextView, timestamp: String) {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp.toLong()
    val format = SimpleDateFormat.getDateTimeInstance()
    textView.text = format.format(calendar.time)
}