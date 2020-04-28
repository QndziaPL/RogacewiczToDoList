package com.rogacewicz

import androidx.lifecycle.LiveData

class TaskRepository(private val taskDao: TaskDao) {

    val allTasks: LiveData<List<Task>> = taskDao.getTasks()

    suspend fun insert(task: Task) {
        taskDao.insert(task)
    }

    suspend fun delete(task: Task){
        taskDao.delete(task)
    }

    suspend fun edit(taskOld: Task, taskNew: Task) {
        taskDao.edit(taskOld.taskName, taskNew.taskName, taskNew.taskDesc)
    }


    suspend fun changeStatus(task: Task) {
        taskDao.editStatus(task)
    }


//    suspend fun changeStatus(index :Int, b: Boolean) {
//        taskDao.changeStatus(index, b)
//    }

    suspend fun deleteAll() {
        taskDao.deleteAll()
    }

}