package com.example.faith.di

import android.os.Build.VERSION_CODES.O
import androidx.annotation.RequiresApi
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class DateSerializer : JsonDeserializer<Date?> {
    override fun deserialize(
        json: JsonElement,
        typfOfT: Type,
        context: JsonDeserializationContext
    ): Date? {
        return try {
            var dateStr = json.asString
            var dateStr2 = dateStr.split(".")
            dateStr = dateStr2[0]


            val locale = Locale("nl","NL")
            var inputPattern = "yyyy-MM-dd'T'HH:mm:ss"
            val inputDateFormat = SimpleDateFormat(inputPattern,locale)
            val inputDate = inputDateFormat.parse(dateStr)
            return inputDate

           

        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }
}