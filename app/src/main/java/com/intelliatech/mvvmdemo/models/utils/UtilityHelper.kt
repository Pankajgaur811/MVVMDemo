package com.intelliatech.mvvmdemo.models.utils

import java.text.SimpleDateFormat
import java.util.*

class UtilityHelper {

    companion object {
        fun convertMiliSecondToDate(format: String, milisecond: Long): String {
            val simpleDateFormat = SimpleDateFormat(format)
            var calendar = Calendar.getInstance()
            calendar.timeInMillis = milisecond
            val dateString = simpleDateFormat.format(calendar.time)
            return dateString
        }
    }
}