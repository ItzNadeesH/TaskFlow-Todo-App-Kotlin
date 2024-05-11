package com.example.taskflow.database.repositories

import com.example.taskflow.database.TaskDatabase
import com.example.taskflow.database.entities.Task

class TaskRepository(private val db: TaskDatabase) {
    suspend fun insert(task: Task) = db.getTaskDao().insertTask(task)
    suspend fun update(task: Task) = db.getTaskDao().updateTask(task)
    suspend fun delete(task: Task) = db.getTaskDao().deleteTask(task)
    fun getTasks(): List<Task> = db.getTaskDao().getAllTasks()
}