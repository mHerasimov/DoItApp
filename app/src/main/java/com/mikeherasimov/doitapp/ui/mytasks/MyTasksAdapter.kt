package com.mikeherasimov.doitapp.ui.mytasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.mikeherasimov.doitapp.databinding.ItemTaskBinding
import com.mikeherasimov.doitapp.io.db.Task

class MyTasksAdapter(
    private val editClickListener: (itemPos: Int) -> Unit = { },
    private val deleteClickListener: (itemPos: Int) -> Unit = { }
): ListAdapter<Task, MyTasksAdapter.ViewHolder>(DiffCallback()) {

    private val viewBinderHelper = ViewBinderHelper().apply { setOpenOnlyOne(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = getItem(position)
        viewBinderHelper.bind(holder.getSwipeRevealLayout(), task.id)
        holder.apply {
            bind(task, editClickListener, deleteClickListener)
            itemView.tag = task
        }
    }

    class ViewHolder(
        private val binding: ItemTaskBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(
            task: Task,
            editClickListener: (itemPos: Int) -> Unit,
            deleteClickListener: (itemPos: Int) -> Unit
        ) {
            binding.task = task
            binding.editClickListener = View.OnClickListener {
                editClickListener(adapterPosition)
            }
            binding.deleteClickListener = View.OnClickListener {
                deleteClickListener(adapterPosition)
            }
            binding.executePendingBindings()
        }

        fun getSwipeRevealLayout() = binding.swipeRevealLayout

    }

    private class DiffCallback: DiffUtil.ItemCallback<Task>() {

        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }

    }

}