package com.mikeherasimov.doitapp.ui.util

import android.widget.RadioButton
import android.widget.RadioGroup

fun RadioGroup.getSelectedRadioButtonTitle(): String {
    val checkedRadioButton = findViewById<RadioButton>(checkedRadioButtonId)
    return checkedRadioButton.text.toString()
}