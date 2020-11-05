package com.example.faith.data

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime

/**
 * @author Jef Seys
 */
data class ApiBericht(
    @field:SerializedName("berichtId") val berichtId: Int,
    @field:SerializedName("verstuurderEmail") val verstuurderEmail: String,
    @field:SerializedName("ontvangerEmail") val ontvangerEmail: String,
    @field:SerializedName("verstuurderNaam") val verstuurderNaam: String,
    @field:SerializedName("ontvangerNaam") val ontvangerNaam: String,
    @field:SerializedName("text") val text: String,
    @field:SerializedName("datum") val datum: String
)