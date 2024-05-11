package com.example.taskflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskflow.database.entities.Task

class MainActivity : AppCompatActivity() {
    private val taskList = mutableListOf<Task>()
    init {
        taskList.add(Task("Hannah Ice", "Anyone else here from New York?", "Passed"))
        taskList.add(Task("Jane Smith", "Sunsets are so beautiful.", "Passed"))
        taskList.add(Task("George Hill", "Anyone else here from New York?", "Passed"))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}