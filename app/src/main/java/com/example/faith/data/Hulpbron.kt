package com.example.faith.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "hulpbronnen")
data class Hulpbron(
        @PrimaryKey @ColumnInfo(name = "id")
        val hulpbronId: Int,
        val titel: String,
        val inhoud: String?,
        val url: String?,
        val telefoonnummer: String?,
        val emailadres: String?,
        val chatUrl: String?,
        val datum: Date?
) {
        override fun toString() = titel
}
