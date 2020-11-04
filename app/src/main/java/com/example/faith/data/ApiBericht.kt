package com.example.faith.data

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime

data class ApiBericht(
    @field:SerializedName("berichtId") val berichtId: Int,
    @field:SerializedName("verstuurder") val verstuurder: String,
    @field:SerializedName("ontvanger") val ontvanger: String,
    @field:SerializedName("text") val text: String,
    @field:SerializedName("datum") val datum: String
)