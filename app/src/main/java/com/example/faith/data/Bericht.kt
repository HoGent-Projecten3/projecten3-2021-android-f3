package com.example.faith.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Date

/**
 * @author Jef Seys
 */
@Entity(tableName = "berichten")
data class Bericht(
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("berichtId")
    val berichtId: Int = 0,
    @field:SerializedName("verstuurderEmail")
    val verstuurderEmail: String,
    @field:SerializedName("ontvangerEmail")
    val ontvangerEmail: String,
    @field:SerializedName("verstuurderNaam")
    val verstuurderNaam: String,
    @field:SerializedName("ontvangerNaam")
    val ontvangerNaam: String,
    @field:SerializedName("text")
    val text: String,
    @field:SerializedName("datum")
    val datum: Date
) {

    fun dateToString(): String {
        return SimpleDateFormat("HH:mm:ss").format(datum)
    }
}
