package com.example.faith.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * @author Remi Mestdagh
 */

class Converters {
    @TypeConverter
    fun dateToString(date: LocalDate): String = date.toString()

    @TypeConverter
    fun stringToDate(value: String): LocalDate =
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate.parse(value)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
}


