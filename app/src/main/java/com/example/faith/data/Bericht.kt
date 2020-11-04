package com.example.faith.data

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.*

data class Bericht (
        //@PrimaryKey @ColumnInfo(name = "id") val berichtId: Int,
        val berichtId: Int,
        val verstuurder: Gebruiker?,
        val ontvanger: Gebruiker?,
        val text: String?,
        val datum: LocalDateTime?
){
}