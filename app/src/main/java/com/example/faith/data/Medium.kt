package com.example.faith.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

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
    val url:String,
    @field:SerializedName("beschrijving")
    val beschrijving: String,
    @field:SerializedName("mediumType")
    val mediumType: Int





) {

    override fun toString() = naam
}
