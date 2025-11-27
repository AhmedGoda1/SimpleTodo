package com.example.simpletodo.data

import kotlinx.coroutines.flow.Flow

class TaskRepository(
    private val dao: TaskDao
) {
    val tasks: Flow<List<Task>> = dao.getTasks()

    suspend fun addTask(task: Task) {
        dao.insertTask(task)
    }

    suspend fun updateTask(task: Task) {
        dao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        dao.deleteTask(task)
    }
}
