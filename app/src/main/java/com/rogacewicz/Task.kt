package com.rogacewicz

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey
    @ColumnInfo(name = "task_name") var taskName: String,
    @ColumnInfo(name = "task_desc") var taskDesc: String = "",
    @ColumnInfo(name = "is_done") var isDone: Boolean = false
)

//data class Task (
//    @PrimaryKey
//    @ColumnInfo(name = "task_name") var taskName: String,
//    @ColumnInfo(name = "is_done") var isDone: Boolean = false
//)
