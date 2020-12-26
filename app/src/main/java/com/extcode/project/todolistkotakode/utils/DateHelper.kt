package com.extcode.project.todolistkotakode.utils

import java.text.SimpleDateFormat
import java.util.*

object DateHelper {
    fun getDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }
}