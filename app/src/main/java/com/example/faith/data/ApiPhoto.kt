package com.example.faith.data

import com.google.gson.annotations.SerializedName

data class ApiPhoto(
    @field:SerializedName("naam") val naam: String,
    @field:SerializedName("url") val url:String,
    @field:SerializedName("mediumId") val mediumId:Int
) {

}