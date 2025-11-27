package com.example.simpletodo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletodo.data.Task
import com.example.simpletodo.data.TaskDatabase
import com.example.simpletodo.data.TaskRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simpletodo.viewmodel.TaskViewModel


// ViewModel for tasks (handles UI state + Room calls)
class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository

    // list of tasks shown on the list screen
    var tasks by mutableStateOf<List<Task>>(emptyList())
        private set

    // fields used on the Add/Edit screen
    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    // simple validation error
    var titleError by mutableStateOf<String?>(null)
        private set

    init {
        val db = TaskDatabase.getDatabase(application)
        val dao = db.taskDao()
        repository = TaskRepository(dao)

        // load tasks from Room and keep updating when they change
        viewModelScope.launch {
            repository.tasks.collectLatest { list ->
                tasks = list
            }
        }
    }

    fun onTitleChange(newTitle: String) {
        title = newTitle
        if (newTitle.isNotBlank()) {
            titleError = null
        }
    }

    fun onDescriptionChange(newDescription: String) {
        description = newDescription
    }

    fun addTask(): Boolean {
        if (title.isBlank()) {
            titleError = "Title can't be empty"
            return false
        }

        val task = Task(
            title = title,
            description = description
        )

        viewModelScope.launch {
            repository.addTask(task)
        }

        // clear inputs after adding
        title = ""
        description = ""
        titleError = null

        return true
    }


    fun toggleDone(task: Task) {
        viewModelScope.launch {
            repository.updateTask(
                task.copy(isDone = !task.isDone)
            )
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }
}
