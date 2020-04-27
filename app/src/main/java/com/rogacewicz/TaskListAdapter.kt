package com.rogacewicz

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class TaskListAdapter internal constructor(
    context: Context,
    private val delegate: Operations
) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var tasks = emptyList<Task>()

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var taskName: TextView = itemView.findViewById(R.id.taskNameTV)
        val editBtn: Button = itemView.findViewById(R.id.editBtn)
        val deleteBtn: Button = itemView.findViewById(R.id.deleteBtn)
        val isDoneBox: CheckBox = itemView.findViewById(R.id.isDoneBox)
        val taskRowLayout: ConstraintLayout = itemView.findViewById(R.id.taskRowLayout)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = inflater.inflate(R.layout.task_row, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val current = tasks[position]

        holder.taskName.text = current.taskName

        holder.deleteBtn.setOnClickListener {
            val builder = AlertDialog.Builder(it.context)
            builder.setMessage(R.string.delete_confirmation_question)
            builder.setPositiveButton(R.string.ok_delete_confirm_button) { _: DialogInterface, _: Int ->
                delegate.delete(position)
            }
            builder.setNegativeButton(
                R.string.cancel_delete_confirm_button
            ) { _: DialogInterface, _: Int -> }
            builder.show()


//            delegate.delete(position)
        }
        holder.editBtn.setOnClickListener {
            delegate.edit(position, holder.itemView)
        }
        holder.isDoneBox.setOnClickListener {
            delegate.changeStatus(position, holder.isDoneBox.isChecked)

        }

        holder.isDoneBox.isChecked = current.isDone

        if (holder.isDoneBox.isChecked) {
            holder.taskRowLayout.setBackgroundResource(R.drawable.task_row_background_checked)
        } else {
            holder.taskRowLayout.setBackgroundResource(R.drawable.task_row_background)
        }


    }

    internal fun setTasks(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    override fun getItemCount() = tasks.size


}

interface Operations {
    fun delete(index: Int)
    fun edit(index: Int, v: View)
    fun changeStatus(index: Int, b: Boolean)

}


