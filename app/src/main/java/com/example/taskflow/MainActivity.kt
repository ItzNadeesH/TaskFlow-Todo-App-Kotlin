package com.example.taskflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskflow.adapters.TaskAdapter
import com.example.taskflow.database.TaskDatabase
import com.example.taskflow.database.entities.Task
import com.example.taskflow.database.repositories.TaskRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: TaskAdapter
    private lateinit var fab: FloatingActionButton
    private lateinit var viewModel:MainActivityData
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.tasksRecyclerView)

        val repository = TaskRepository(TaskDatabase.getInstance(this))
        viewModel = ViewModelProvider(this)[MainActivityData::class.java]
        fab = findViewById(R.id.fab)
        fab.setOnClickListener {
            displayDialog(repository)
        }
        viewModel.data.observe(this){
            adapter = TaskAdapter(it, repository)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }
        CoroutineScope(Dispatchers.IO).launch {
            val data = repository.getTasks()
            runOnUiThread {
                viewModel.setData(data)
            }
        }
        val itemTouchHelper = ItemTouchHelper(RecyclerViewTouchHelper(viewModel, recyclerView, repository,this))
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }
    private fun displayDialog(repository: TaskRepository){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Enter Your New Task:")
        builder.setMessage("Enter the todo item below:")
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)
        builder.setPositiveButton("OK") { dialog, which ->
            val item = input.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                repository.insert(Task(item, false))
                val data = repository.getTasks()
                runOnUiThread {
                    viewModel.setData(data)
                }
            }
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.cancel()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}