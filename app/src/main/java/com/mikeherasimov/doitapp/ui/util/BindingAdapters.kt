package com.mikeherasimov.doitapp.ui.util

import android.util.Log
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.view.get
import androidx.databinding.BindingAdapter
import androidx.databinding.Observable
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
    calendar.timeInMillis = timestamp.get() * 1000
    val format = SimpleDateFormat.getDateInstance()
    editText.setText(format.format(calendar.time))
}

@BindingAdapter("time")
fun bindTime(editText: EditText, timestamp: ObservableLong) {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp.get() * 1000
    val format = SimpleDateFormat.getTimeInstance()
    editText.setText(format.format(calendar.time))
}

@BindingAdapter("dateAndTime")
fun bindDateAndTime(textView: TextView, timestamp: String) {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp.toLong() * 1000
    val format = SimpleDateFormat.getDateTimeInstance()
    textView.text = format.format(calendar.time)
}

/*
@BindingAdapter("priority")
fun bindPriority(radioGroup: RadioGroup, priority: ObservableField<String>) {
    radioGroup.setOnCheckedChangeListener { group, i ->
        if (isStatesEqual(radioGroup, priority)) {
            return@setOnCheckedChangeListener
        }
        val checkedRadioButton = radioGroup.findViewById<RadioButton>(i)
        priority.set(checkedRadioButton.text.toString())
    }
    priority.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            if (isStatesEqual(radioGroup, priority)) {
                return
            }
            for (i in 0..radioGroup.childCount) {
                val radioButton = radioGroup.getChildAt(i) as RadioButton
                Log.d("Priority", radioButton.text.toString() + " " + (sender as ObservableField<String>).get())
                if (radioButton.text.toString() == (sender as ObservableField<String>).get()) {
                    radioGroup.check(radioGroup.id)
                }
            }
        }
    })
}

private fun isStatesEqual(radioGroup: RadioGroup, priority: ObservableField<String>): Boolean {
    val checkedRadioButton = radioGroup.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
    Log.d("Priority", "states " + checkedRadioButton.text.toString() + " " + priority.get())
    if (checkedRadioButton.text.toString() == priority.get()) {
        return true
    }
    return false
}*/
