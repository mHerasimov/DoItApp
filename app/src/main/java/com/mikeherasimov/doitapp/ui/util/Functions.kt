package com.mikeherasimov.doitapp.ui.util

import android.content.Context
import android.widget.RadioButton
import android.widget.RadioGroup
import com.mikeherasimov.doitapp.R
import com.valdesekamdem.library.mdtoast.MDToast

fun RadioGroup.getSelectedRadioButtonTitle(): String {
    val checkedRadioButton = findViewById<RadioButton>(checkedRadioButtonId)
    return checkedRadioButton.text.toString()
}

fun showNoInternetToast(context: Context) {
    val mdToast = MDToast.makeText(
        context,
        context.getString(R.string.warning_no_internet_connection),
        MDToast.LENGTH_SHORT,
        MDToast.TYPE_WARNING
    )
    mdToast.show()
}