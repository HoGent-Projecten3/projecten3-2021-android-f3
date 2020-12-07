package com.example.faith.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "hulpbronnen")
data class Hulpbron(
        @PrimaryKey @ColumnInfo(name = "id")
        val hulpbronId: Int,
        val auteurType:String,
        val titel: String,
        val inhoud: String?,
        val url: String?,
        val telefoonnummer: String?,
        val emailadres: String?,
        val chatUrl: String?,
        val datum: String
) {
        override fun toString() = titel
}
