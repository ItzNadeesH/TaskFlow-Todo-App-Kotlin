package com.example.taskflow.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskflow.R
import com.example.taskflow.TaskViewHolder
import com.example.taskflow.database.entities.Task

class TaskAdapter(private val data: List<Task>): RecyclerView.Adapter<TaskViewHolder>() {
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
        if (singleData.status) {
            holder.taskName.isChecked = true
        }
    }
}