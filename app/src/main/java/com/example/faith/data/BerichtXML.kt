package com.example.faith.data

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDateTime

class BerichtXML (
        var verstuurderEmail: String,
        var ontvangerEmail: String,
        var verstuurderNaam: String,
        var ontvangerNaam: String,
        var text: String,
        var datum: LocalDateTime
){
}