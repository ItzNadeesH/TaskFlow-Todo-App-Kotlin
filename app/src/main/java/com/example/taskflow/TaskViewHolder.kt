package com.example.taskflow

import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView

class TaskViewHolder(itemView: View, parent: ViewGroup) : RecyclerView.ViewHolder(itemView) {
    val taskName: CheckBox = itemView.findViewById(R.id.taskName)
}