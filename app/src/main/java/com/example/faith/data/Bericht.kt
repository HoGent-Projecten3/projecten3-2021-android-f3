package com.example.faith.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.*

@Entity(tableName="berichten", indices = [Index(value = ["id"], unique = true)])
data class Bericht (
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var berichtId: Int?,
        var verstuurder: String,
        var ontvanger: String,
        var text: String,
        var datum: String
){
}