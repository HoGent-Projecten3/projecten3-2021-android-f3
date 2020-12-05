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

@RequiresApi(O)
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
            var sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            var joost = LocalDate.parse(dateStr, sdf)
            Date.from(joost.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }
}