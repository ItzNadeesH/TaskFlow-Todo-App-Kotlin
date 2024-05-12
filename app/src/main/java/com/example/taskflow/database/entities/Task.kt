package com.example.taskflow.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Task(var taskName: String, var status: Boolean) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}

