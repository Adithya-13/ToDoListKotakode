package com.extcode.project.todolistkotakode.ui.main

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.extcode.project.todolistkotakode.data.source.local.entity.Todo
import com.extcode.project.todolistkotakode.databinding.ToDoItemBinding
import com.extcode.project.todolistkotakode.ui.detail.DetailActivity
import java.util.*

class TodoAdapter(private val activity: Activity) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private val listTodo = ArrayList<Todo>()

    fun setTodoList(todoList: List<Todo>) {
        listTodo.clear()
        listTodo.addAll(todoList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ToDoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(listTodo[position])
    }

    override fun getItemCount(): Int = listTodo.size

    inner class TodoViewHolder(private val binding: ToDoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            with(binding) {
                title.text = todo.title
                description.text = todo.description
                date.text = todo.date
                cvToDoItem.setOnClickListener {
                    val intent = Intent(activity, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_POSITION, adapterPosition)
                    intent.putExtra(DetailActivity.EXTRA_TODO, todo)
                    activity.startActivityForResult(intent, DetailActivity.REQUEST_UPDATE)
                }
            }
        }
    }
}