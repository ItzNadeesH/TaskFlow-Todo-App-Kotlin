package com.example.taskflow.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.taskflow.database.entities.Task
@Dao
interface TaskDao {
    @Insert
    suspend fun insertTask(task: Task)
    @Update
    suspend fun updateTask(task: Task)
    @Delete
    suspend fun deleteTask(task: Task)
    @Query("SELECT * FROM Task")
    fun getAllTasks(): List<Task>
}