package com.example.faith.data

import android.provider.SyncStateContract
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
/**
 * @author Remi Mestdagh
 */
class Converters {

    @TypeConverter
    fun toJson(torrent: List<Medium>): String {
        val type = object : TypeToken<List<Medium>>() {}.type
        return Gson().toJson(torrent, type)
    }

}
