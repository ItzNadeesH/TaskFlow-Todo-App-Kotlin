package com.example.taskflow.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskflow.R
import com.example.taskflow.TaskViewHolder
import com.example.taskflow.database.entities.Task
import com.example.taskflow.database.repositories.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskAdapter(private val data: List<Task>, private val repository: TaskRepository): RecyclerView.Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.task_layout, parent, false)
        return TaskViewHolder(view, parent)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val singleData = data[position]
        holder.taskName.text = singleData.taskName
        holder.taskName.isChecked = singleData.status // Set the initial checked state

        holder.taskName.setOnCheckedChangeListener { _, isChecked ->
            // Update the status of the task in the database when the checkbox state changes
            if (isChecked != singleData.status) {
                singleData.status = isChecked
                CoroutineScope(Dispatchers.IO).launch {
                    repository.update(singleData)
                }
            }
        }
    }
}