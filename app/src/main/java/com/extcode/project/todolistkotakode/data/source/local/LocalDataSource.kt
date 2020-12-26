package com.extcode.project.todolistkotakode.data.source.local

import androidx.lifecycle.LiveData
import com.extcode.project.todolistkotakode.data.source.local.entity.Todo
import com.extcode.project.todolistkotakode.data.source.local.room.TodoDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LocalDataSource private constructor(private val mTodoDao: TodoDao) {

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(todoDao: TodoDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(todoDao)
    }

    fun getAllTodo(): LiveData<List<Todo>> = mTodoDao.getAllTodo()

    fun insert(todo: Todo) {
        executorService.execute { mTodoDao.insert(todo) }
    }

    fun update(todo: Todo) {
        executorService.execute { mTodoDao.update(todo) }
    }

    fun delete(todo: Todo) {
        executorService.execute { mTodoDao.delete(todo) }
    }

}