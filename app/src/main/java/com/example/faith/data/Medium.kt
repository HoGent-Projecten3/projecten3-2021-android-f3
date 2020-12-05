package com.example.faith.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

/**
 * @author Remi Mestdagh
 */
@Entity(tableName = "media")
data class Medium(
    @PrimaryKey
    @field:SerializedName("mediumId")
    val mediumId: Int,
    @field:SerializedName("naam")
    val naam: String,

    @field:SerializedName("url")
    val url: String,
    @field:SerializedName("beschrijving")
    val beschrijving: String,
    @field:SerializedName("mediumType")
    val mediumType: Int,
    @field:SerializedName("datum")
    val datum: Date

) {

    override fun toString() = naam
    fun toSimpleString(): String  {
       return SimpleDateFormat("dd/MM/yyy").format(datum)
    }
}
