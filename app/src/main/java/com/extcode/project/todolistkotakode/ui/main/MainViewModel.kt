package com.extcode.project.todolistkotakode.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.extcode.project.todolistkotakode.data.TodoRepository
import com.extcode.project.todolistkotakode.data.source.local.entity.Todo

class MainViewModel(private val todoRepository: TodoRepository) : ViewModel() {
    fun getAllTodo(): LiveData<List<Todo>> = todoRepository.getAllTodo()
}