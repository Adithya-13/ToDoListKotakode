package com.extcode.project.todolistkotakode.di

import android.content.Context
import com.extcode.project.todolistkotakode.data.TodoRepository
import com.extcode.project.todolistkotakode.data.source.local.LocalDataSource
import com.extcode.project.todolistkotakode.data.source.local.room.TodoDatabase

object Injection {

    fun provideRepository(context: Context): TodoRepository {

        val database = TodoDatabase.getInstance(context)

        val localDataSource = LocalDataSource.getInstance(database.todoDao())

        return TodoRepository.getInstance(localDataSource)
    }
}