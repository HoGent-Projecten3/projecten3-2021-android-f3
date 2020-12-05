package com.example.faith.di

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type
import java.util.Date

class DateSerializer : JsonDeserializer<Date?> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Date {
        val s = json.asJsonPrimitive.asString
        val l = s.substring(6, s.length - 2).toLong()
        return Date(l)
    }
}