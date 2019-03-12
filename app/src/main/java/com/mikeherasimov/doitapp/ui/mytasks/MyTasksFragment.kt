package com.mikeherasimov.doitapp.ui.mytasks

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.mikeherasimov.doitapp.App
import com.mikeherasimov.doitapp.R

import com.mikeherasimov.doitapp.databinding.FragmentMyTasksBinding
import com.mikeherasimov.doitapp.io.data.TaskRepository
import com.mikeherasimov.doitapp.io.data.UserRepository
import com.mikeherasimov.doitapp.ui.signin.SignInActivity
import javax.inject.Inject

class MyTasksFragment : Fragment() {

    @Inject
    lateinit var userRepository: UserRepository
    @Inject
    lateinit var taskRepository: TaskRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        App.component.inject(this)

        val viewModel = setupViewModel()
        viewModel.isUserLoggedIn.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (!(sender as ObservableBoolean).get()) {
                    val intent = Intent(activity, SignInActivity::class.java)
                    activity!!.startActivityForResult(intent, 1)
                }
            }
        })

        val binding = FragmentMyTasksBinding.inflate(inflater, container, false)
        binding.fabClickListener = View.OnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_myTasksFragment_to_addEditTaskFragment)
        }
        return binding.root
    }

    private fun setupViewModel(): MyTasksViewModel {
        val factory = MyTasksViewModel.Factory(userRepository, taskRepository)
        return ViewModelProviders
            .of(this, factory)
            .get(MyTasksViewModel::class.java)
    }

}
