package com.mikeherasimov.doitapp.ui.util

import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import com.google.android.material.textfield.TextInputLayout

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