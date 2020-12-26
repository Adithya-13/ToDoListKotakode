package com.extcode.project.todolistkotakode.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.extcode.project.todolistkotakode.R
import com.extcode.project.todolistkotakode.data.source.local.entity.Todo
import com.extcode.project.todolistkotakode.databinding.ActivityMainBinding
import com.extcode.project.todolistkotakode.ui.detail.DetailActivity
import com.extcode.project.todolistkotakode.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private var _activityMainBinding: ActivityMainBinding? = null
    private val binding get() = _activityMainBinding!!

    private lateinit var adapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        viewModel.getAllTodo().observe(this, todoObserver)
        adapter = TodoAdapter(this)

        binding.rvToDo.layoutManager = LinearLayoutManager(this)
        binding.rvToDo.hasFixedSize()
        binding.rvToDo.adapter = adapter

        binding.addFab.setOnClickListener {
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            startActivityForResult(intent, DetailActivity.REQUEST_ADD)
        }
    }

    private val todoObserver = Observer<List<Todo>> { todoList ->
        if (todoList != null){
            Log.d("anjay", todoList.toString())
            adapter.setTodoList(todoList)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (requestCode == DetailActivity.REQUEST_ADD) {
                if (resultCode == DetailActivity.RESULT_ADD) {
                    showToast(getString(R.string.added))
                }
            } else if (requestCode == DetailActivity.REQUEST_UPDATE) {
                if (resultCode == DetailActivity.RESULT_UPDATE) {
                    showToast(getString(R.string.changed))
                } else if (resultCode == DetailActivity.RESULT_DELETE) {
                    showToast(getString(R.string.deleted))
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }
}