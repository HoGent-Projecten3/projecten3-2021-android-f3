package com.example.faith.data

import androidx.room.TypeConverter
import java.util.Date

/**
 * @author Remi Mestdagh
 */

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
