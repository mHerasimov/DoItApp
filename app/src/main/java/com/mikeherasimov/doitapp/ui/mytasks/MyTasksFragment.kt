package com.mikeherasimov.doitapp.ui.mytasks

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        val adapter = setupAdapter()
        val binding = FragmentMyTasksBinding.inflate(inflater, container, false)
        binding.rvTasks.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            setupScrollListener(this, viewModel)
        }
        binding.fabClickListener = View.OnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_myTasksFragment_to_addEditTaskFragment)
        }
        subscribeUi(viewModel, adapter)
        return binding.root
    }

    private fun setupViewModel(): MyTasksViewModel {
        val factory = MyTasksViewModel.Factory(userRepository, taskRepository)
        return ViewModelProviders
            .of(this, factory)
            .get(MyTasksViewModel::class.java)
    }

    private fun setupAdapter(): MyTasksAdapter {
        return MyTasksAdapter()
    }

    private fun subscribeUi(viewModel: MyTasksViewModel, adapter: MyTasksAdapter) {
        viewModel.isUserLoggedIn.observe(this, Observer {
            if (!it) {
                val intent = Intent(context, SignInActivity::class.java)
                activity!!.startActivityForResult(intent, 1)
            }
        })
        // TODO observe network error here
        viewModel.search("title asc").observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun setupScrollListener(recycler: RecyclerView, viewModel: MyTasksViewModel) {
        val layoutManager = recycler.layoutManager as androidx.recyclerview.widget.LinearLayoutManager
        recycler.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                viewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })
    }

}
