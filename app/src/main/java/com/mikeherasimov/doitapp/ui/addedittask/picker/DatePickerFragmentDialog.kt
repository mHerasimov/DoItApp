package com.mikeherasimov.doitapp.ui.addedittask.picker

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragmentDialog: DialogFragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var listener: DatePickerListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = targetFragment as DatePickerListener
        } catch (e: ClassCastException) {
            throw ClassCastException(targetFragment.toString()
                    + " must implement DatePickerListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(activity!!, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        listener.onDatePicked(year, month, day)
    }

    interface DatePickerListener {
        fun onDatePicked(year: Int, month: Int, day: Int)
    }

}