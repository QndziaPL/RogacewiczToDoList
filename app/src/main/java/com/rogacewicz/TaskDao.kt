package com.rogacewicz

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface TaskDao {
    @Query("SELECT * FROM task_table")
    fun getTasks(): LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("DELETE FROM task_table")
    suspend fun deleteAll()

    @Query("UPDATE task_table SET task_name= :task_new WHERE task_name= :task_old")
    suspend fun edit(task_old: String, task_new: String)

    @Update
    suspend fun editStatus(task: Task)

}