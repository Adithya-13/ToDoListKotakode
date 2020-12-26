package com.extcode.project.todolistkotakode.data

import androidx.lifecycle.LiveData
import com.extcode.project.todolistkotakode.data.source.local.LocalDataSource
import com.extcode.project.todolistkotakode.data.source.local.entity.Todo

class TodoRepository private constructor(
    private val localDataSource: LocalDataSource,
) {

    companion object {
        @Volatile
        private var instance: TodoRepository? = null

        fun getInstance(
            localData: LocalDataSource,
        ): TodoRepository =
            instance ?: synchronized(this) {
                instance ?: TodoRepository(localData)
            }
    }

    fun getAllTodo(): LiveData<List<Todo>> = localDataSource.getAllTodo()

    fun insert(todo: Todo) = localDataSource.insert(todo)

    fun update(todo: Todo) = localDataSource.update(todo)

    fun delete(todo: Todo) = localDataSource.delete(todo)
}