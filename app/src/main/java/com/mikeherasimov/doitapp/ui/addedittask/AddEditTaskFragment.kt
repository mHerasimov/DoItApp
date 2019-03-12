package com.mikeherasimov.doitapp.ui.addedittask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mikeherasimov.doitapp.databinding.FragmentAddEditTaskBinding
import com.mikeherasimov.doitapp.ui.addedittask.picker.DatePickerFragmentDialog
import com.mikeherasimov.doitapp.ui.addedittask.picker.TimePickerFragmentDialog

class AddEditTaskFragment : Fragment(),
    DatePickerFragmentDialog.DatePickerListener, TimePickerFragmentDialog.TimePickerListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAddEditTaskBinding.inflate(inflater, container, false)
        binding.pickDateClickListener = View.OnClickListener {
            val dialog = DatePickerFragmentDialog()
            dialog.setTargetFragment(this, 1)
            dialog.show(fragmentManager!!, "")
        }
        binding.pickTimeClickListener = View.OnClickListener {
            val dialog = TimePickerFragmentDialog()
            dialog.setTargetFragment(this, 1)
            dialog.show(fragmentManager!!, "")
        }

        return binding.root
    }

    override fun onDatePicked(year: Int, month: Int, day: Int) {

    }

    override fun onTimePicked(hourOfDay: Int, minute: Int) {

    }

}
