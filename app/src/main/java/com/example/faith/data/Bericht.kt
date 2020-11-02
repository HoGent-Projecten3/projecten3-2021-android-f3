package com.example.faith.data

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import java.util.*

data class Bericht (
        //@PrimaryKey @ColumnInfo(name = "id") val berichtId: Int,
        val verstuurder: Gebruiker?,
        val text: String?,
        val datum: Date?
){
}