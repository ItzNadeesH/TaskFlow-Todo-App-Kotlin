package com.example.taskflow.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Task(var name: String, var dueDate: Date, var status: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}

