package com.example.taskflow

import android.app.AlertDialog
import android.content.Context
import android.text.InputType
import android.widget.EditText
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskflow.adapters.TaskAdapter
import com.example.taskflow.database.repositories.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecyclerViewTouchHelper(private var viewModel: MainActivityData, val recyclerView: RecyclerView, private val repository: TaskRepository, private val context: Context): ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        if (direction == ItemTouchHelper.RIGHT) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete Task")
            builder.setMessage("Are You Sure ?")
            builder.setPositiveButton("Yes") { dialog, which ->
                CoroutineScope(Dispatchers.IO).launch {
                    val taskList = repository.getTasks()
                    repository.delete(taskList[position])
                    withContext(Dispatchers.Main) {
                        // Recreate the activity
                        (context as MainActivity).recreate()
                    }
                }
            }
            builder.setNegativeButton("Cancel") { dialog, which ->
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        // Recreate the activity
                        (context as MainActivity).recreate()
                    }
                }
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        } else {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Update your task")
            builder.setMessage("Enter the task below:")

            val linearLayout = LinearLayout(context)
            linearLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            val input = EditText(context)
            input.inputType = InputType.TYPE_CLASS_TEXT

            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            val horizontalMargin = 50 // Adjust as needed
            layoutParams.setMargins(horizontalMargin, 0, horizontalMargin, 0)
            input.layoutParams = layoutParams

            CoroutineScope(Dispatchers.IO).launch {
                val taskList = repository.getTasks()
                input.setText(taskList[position].taskName)
            }

            // Add EditText to LinearLayout
            linearLayout.addView(input)

            builder.setView(linearLayout)
            builder.setPositiveButton("Yes") { dialog, which ->
                val item = input.text.toString()
                CoroutineScope(Dispatchers.IO).launch {
                    val taskList = repository.getTasks()
                    taskList[position].taskName = item
                    repository.update(taskList[position])
                    withContext(Dispatchers.Main) {
                        // Recreate the activity
                        (context as MainActivity).recreate()
                    }
                }
            }
            builder.setNegativeButton("Cancel") { dialog, which ->
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        // Recreate the activity
                        (context as MainActivity).recreate()
                    }
                }
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }
}