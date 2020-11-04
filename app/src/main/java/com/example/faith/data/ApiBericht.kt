package com.example.faith.data

import com.google.gson.annotations.SerializedName

data class ApiBericht(
    @field:SerializedName("berichtId") val berichtId: Int,
    @field:SerializedName("verstuurder") val verstuurder: String,
    @field:SerializedName("ontvanger") val ontvanger: String,
    @field:SerializedName("text") val text: String
)