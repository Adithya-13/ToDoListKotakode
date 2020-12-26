package com.extcode.project.todolistkotakode.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.extcode.project.todolistkotakode.data.source.local.entity.Todo


@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(todo: Todo)

    @Update
    fun update(todo: Todo)

    @Delete
    fun delete(todo: Todo)

    @Query("SELECT * from todo ORDER BY id ASC")
    fun getAllTodo(): LiveData<List<Todo>>
}