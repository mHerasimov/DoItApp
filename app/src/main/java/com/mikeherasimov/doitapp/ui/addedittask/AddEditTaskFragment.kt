package com.mikeherasimov.doitapp.ui.addedittask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.mikeherasimov.doitapp.App
import com.mikeherasimov.doitapp.R

import com.mikeherasimov.doitapp.databinding.FragmentAddEditTaskBinding
import com.mikeherasimov.doitapp.io.data.TaskRepository
import com.mikeherasimov.doitapp.ui.addedittask.picker.DatePickerFragmentDialog
import com.mikeherasimov.doitapp.ui.addedittask.picker.TimePickerFragmentDialog
import javax.inject.Inject

class AddEditTaskFragment : Fragment(),
    DatePickerFragmentDialog.DatePickerListener, TimePickerFragmentDialog.TimePickerListener {

    @Inject
    lateinit var taskRepository: TaskRepository

    private val args by lazy { AddEditTaskFragmentArgs.fromBundle(arguments!!) }
    private lateinit var viewModel: AddEditTaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        App.component.inject(this)
        // TODO reduce amount of lines in this function
        val binding = FragmentAddEditTaskBinding.inflate(inflater, container, false)
        binding.pickDateClickListener = View.OnClickListener {
            val dialog = DatePickerFragmentDialog()
            showPickerDialog(dialog)
        }
        binding.pickTimeClickListener = View.OnClickListener {
            val dialog = TimePickerFragmentDialog()
            showPickerDialog(dialog)
        }

        viewModel = setupViewModel()
        binding.viewModel = viewModel
        binding.fabClickListener = View.OnClickListener {
            if (viewModel.validate()) {
                if (args.isInAddMode) {
                    viewModel.createTask()
                } else {
                    viewModel.updateTask()
                }
            }
        }
        binding.priorityCheckChangeListener = RadioGroup.OnCheckedChangeListener { radioGroup, i ->
            val checkedRadioButton = radioGroup.findViewById(i) as RadioButton
            viewModel.priority.set(checkedRadioButton.text.toString())
        }
        viewModel.onTaskSuccessfullyUpdatedOrCreated.addOnPropertyChangedCallback(
            object: Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    if ((sender as ObservableBoolean).get()) {
                        NavHostFragment.findNavController(this@AddEditTaskFragment).popBackStack()
                    }
                }
            })
        return binding.root
    }

    override fun onDatePicked(year: Int, month: Int, day: Int) {
        viewModel.setDeadlineDate(year, month, day)
    }

    override fun onTimePicked(hourOfDay: Int, minute: Int) {
        viewModel.setDeadlineTime(hourOfDay, minute)
    }

    private fun setupViewModel(): AddEditTaskViewModel {
        val factory = AddEditTaskViewModel.Factory(
            taskRepository,
            getString(R.string.task_priority_high),
            if (args.isInAddMode) 0 else args.taskId,
            args.isInAddMode
        )
        return ViewModelProviders.of(this, factory)
            .get(AddEditTaskViewModel::class.java)
    }

    private fun showPickerDialog(dialog: DialogFragment) {
        dialog.setTargetFragment(this, 1)
        dialog.show(fragmentManager!!, "")
    }

}
