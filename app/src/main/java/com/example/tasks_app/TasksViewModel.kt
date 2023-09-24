package com.example.tasks_app

import android.text.method.TransformationMethod
import android.view.animation.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TasksViewModel(val dao: TaskDao): ViewModel() {
    var newTaskName = ""

     val tasks = dao.getAll()

    private val _navigateToTask = MutableLiveData<Long?>()
    val navigateToTask: LiveData<Long?>
        get() = _navigateToTask


    fun addTask(){
        viewModelScope.launch {
            val task = Task()
            task.taskName = newTaskName
            dao.insert(task)
        }

    }
    fun onTaskedClicked(taskId: Long){
        _navigateToTask.value = taskId
    }
    fun onTaskedNavigated(){
        _navigateToTask.value = null
    }

}