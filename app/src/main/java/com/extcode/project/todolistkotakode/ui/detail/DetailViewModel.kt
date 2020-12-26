package com.extcode.project.todolistkotakode.ui.detail

import androidx.lifecycle.ViewModel
import com.extcode.project.todolistkotakode.data.TodoRepository
import com.extcode.project.todolistkotakode.data.source.local.entity.Todo

class DetailViewModel(private val todoRepository: TodoRepository): ViewModel() {
    fun insert(todo: Todo) = todoRepository.insert(todo)
    fun update(todo: Todo) = todoRepository.update(todo)
    fun delete(todo: Todo) = todoRepository.delete(todo)
}