package com.mikeherasimov.doitapp.ui.addedittask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.mikeherasimov.doitapp.databinding.FragmentAddEditTaskBinding
import com.mikeherasimov.doitapp.ui.addedittask.picker.DatePickerFragmentDialog
import com.mikeherasimov.doitapp.ui.addedittask.picker.TimePickerFragmentDialog
import com.mikeherasimov.doitapp.ui.util.eraseDate
import com.mikeherasimov.doitapp.ui.util.eraseTime
import java.util.*

class AddEditTaskFragment : Fragment(),
    DatePickerFragmentDialog.DatePickerListener, TimePickerFragmentDialog.TimePickerListener {

    lateinit var viewModel: AddEditTaskViewModel

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

        viewModel = setupViewModel()
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onDatePicked(year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance().apply {
            set(year, month, day, 0, 0)
            eraseTime()
        }
        viewModel.dateTimestamp.set(calendar.timeInMillis)
    }

    override fun onTimePicked(hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance().apply {
            set(0, 0, 0, hourOfDay, minute)
            eraseDate()
        }
        viewModel.timeTimestamp.set(calendar.timeInMillis)
    }

    private fun setupViewModel(): AddEditTaskViewModel {
        return ViewModelProviders.of(this).get(AddEditTaskViewModel::class.java)
    }

}
