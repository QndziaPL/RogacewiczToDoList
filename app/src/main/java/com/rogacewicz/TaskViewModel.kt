package com.rogacewicz


import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

// ma pośredniczyć pomiędzy taskmanagerem a fragmentami
@InternalCoroutinesApi
class TaskViewModel(application: Application): AndroidViewModel(application) {

    private val repository: TaskRepository

    val allTasks: LiveData<List<Task>>

    init {
        val taskDao = TaskDatabase.getDatabase(application, viewModelScope).taskDao()
        repository = TaskRepository(taskDao)
        allTasks = repository.allTasks
    }

    fun insert(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(task)
    }

    fun delete(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(task)
    }
    fun edit(taskOld: Task, taskNew: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.edit(taskOld, taskNew)
    }


    fun changeStatus(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        if (!task.isDone){
            task.isDone = true
            repository.changeStatus(task)
        }else{
            task.isDone = false
            repository.changeStatus(task)
        }
    }



//    fun changeStatus(index: Int, b: Boolean) = viewModelScope.launch(Dispatchers.IO) {
//        repository.changeStatus(index, b)
//    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }


}