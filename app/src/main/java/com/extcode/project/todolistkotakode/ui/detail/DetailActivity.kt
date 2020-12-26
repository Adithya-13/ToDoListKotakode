package com.extcode.project.todolistkotakode.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.extcode.project.todolistkotakode.R
import com.extcode.project.todolistkotakode.data.source.local.entity.Todo
import com.extcode.project.todolistkotakode.databinding.ActivityDetailBinding
import com.extcode.project.todolistkotakode.utils.DateHelper
import com.extcode.project.todolistkotakode.viewmodel.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TODO = "extra_todo"
        const val EXTRA_POSITION = "extra_position"
        const val REQUEST_ADD = 100
        const val RESULT_ADD = 101
        const val REQUEST_UPDATE = 200
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 20
        const val ALERT_DIALOG_DELETE = 30
    }

    private var todo: Todo? = null
    private var position = 0
    private var isEdit = false
    private lateinit var detailViewModel: DetailViewModel

    private var _activityDetailBinding: ActivityDetailBinding? = null
    private val binding get() = _activityDetailBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        detailViewModel = ViewModelProvider(this, factory).get(DetailViewModel::class.java)

        todo = intent.getParcelableExtra(EXTRA_TODO)
        if (todo != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        } else {
            todo = Todo()
        }

        val actionBarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)
            if (todo != null) {
                todo?.let { todo ->
                    binding.editTextTitle.setText(todo.title)
                    binding.editTextDescription.setText(todo.description)
                }
            }
        } else {
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.saveButton.text = btnTitle

        binding.saveButton.setOnClickListener {
            val title = binding.editTextTitle.text.toString().trim()
            val description = binding.editTextDescription.text.toString().trim()
            if (title.isEmpty()) {
                binding.editTextTitle.error = getString(R.string.empty)
            } else if (description.isEmpty()) {
                binding.editTextDescription.error = getString(R.string.empty)
            } else {
                todo.let { todo ->
                    todo?.title = title
                    todo?.description = description
                }

                val intent = Intent().apply {
                    putExtra(EXTRA_TODO, todo)
                    putExtra(EXTRA_POSITION, position)
                }

                if (isEdit) {
                    detailViewModel.update(todo as Todo)
                    setResult(RESULT_UPDATE, intent)
                    finish()
                } else {
                    todo.let { note ->
                        note?.date = DateHelper.getDate()
                    }
                    detailViewModel.insert(todo as Todo)
                    setResult(RESULT_ADD, intent)
                    finish()
                }
            }
        }
    }

    override fun onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_detail, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.deleteTodo -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogContent: String
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogContent = getString(R.string.message_cancel)
        } else {
            dialogContent = getString(R.string.message_delete)
            dialogTitle = getString(R.string.delete)
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogContent)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (!isDialogClose) {
                    detailViewModel.delete(todo as Todo)

                    val intent = Intent()
                    intent.putExtra(EXTRA_POSITION, position)
                    setResult(RESULT_DELETE, intent)
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


    override fun onDestroy() {
        super.onDestroy()
        _activityDetailBinding = null
    }
}