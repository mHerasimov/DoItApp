package com.mikeherasimov.doitapp.ui.mytasks

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
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
import com.mikeherasimov.doitapp.ui.util.showNoInternetToast
import javax.inject.Inject

class MyTasksFragment : Fragment(), SortTasksDialog.SortingOrderListener {

    @Inject
    lateinit var userRepository: UserRepository
    @Inject
    lateinit var taskRepository: TaskRepository

    private lateinit var viewModel: MyTasksViewModel
    private lateinit var tasksAdapter: MyTasksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        App.component.inject(this)
        setHasOptionsMenu(true)

        viewModel = setupViewModel()
        tasksAdapter = setupAdapter(viewModel)
        val binding = FragmentMyTasksBinding.inflate(inflater, container, false)
        binding.rvTasks.apply {
            this.adapter = tasksAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            setupScrollListener(this, viewModel)
        }
        binding.fabClickListener = View.OnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_myTasksFragment_to_addEditTaskFragment)
        }
        subscribeUi(viewModel, tasksAdapter)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_my_tasks, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort -> {
                val dialog = SortTasksDialog()
                dialog.setTargetFragment(this, 1)
                dialog.show(fragmentManager, "")
                true
            }
            R.id.action_logout -> {
                viewModel.logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSortingOrderPicked(sortOrder: String) {
        viewModel.search(sortOrder).observe(this, Observer {
            tasksAdapter.submitList(it)
        })
    }

    private fun setupViewModel(): MyTasksViewModel {
        val factory = MyTasksViewModel.Factory(userRepository, taskRepository)
        return ViewModelProviders
            .of(this, factory)
            .get(MyTasksViewModel::class.java)
    }

    private fun setupAdapter(viewModel: MyTasksViewModel): MyTasksAdapter {
        var adapter: MyTasksAdapter? = null
        val editClickListener: (Int) -> Unit = {
            val action =
                MyTasksFragmentDirections.actionMyTasksFragmentToAddEditTaskFragment(
                    false,
                    adapter!!.getItemAt(it)
                )
            NavHostFragment.findNavController(this).navigate(action)
        }
        val deleteClickListener: (Int) -> Unit = {
            viewModel.deleteTask(adapter!!.getItemAt(it).id)
        }
        adapter = MyTasksAdapter(editClickListener, deleteClickListener)
        return adapter
    }

    private fun subscribeUi(viewModel: MyTasksViewModel, adapter: MyTasksAdapter) {
        viewModel.isUserLoggedIn.observe(this, Observer {
            if (!it) {
                openSignInActivity()
            }
        })
        // TODO observe network error here
        viewModel.search("title asc").observe(this, Observer {
            adapter.submitList(it)
        })
        viewModel.networkError.observe(this, Observer {
            if (it) {
                showNoInternetToast(context!!)
            }
        })
    }

    private fun openSignInActivity() {
        val intent = Intent(context, SignInActivity::class.java)
        activity!!.startActivityForResult(intent, 1)
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
