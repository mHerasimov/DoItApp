package com.mikeherasimov.doitapp.ui.addedittask.picker

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragmentDialog: DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private lateinit var listener: TimePickerListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = targetFragment as TimePickerListener
        } catch (e: ClassCastException) {
            throw ClassCastException(targetFragment.toString()
                    + " must implement TimePickerListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        listener.onTimePicked(hourOfDay, minute)
    }

    interface TimePickerListener {
        fun onTimePicked(hourOfDay: Int, minute: Int)
    }

}