package com.example.faith.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.*

@Entity(tableName="berichten")
data class Bericht (
        @PrimaryKey(autoGenerate = true) val berichtId: Int,
        val verstuurder: String,
        val ontvanger: String,
        val text: String,
        val datum: String
){
}