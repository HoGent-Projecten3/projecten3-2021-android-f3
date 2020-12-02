package com.example.faith.data

import com.google.gson.annotations.SerializedName
/**
 * @author Remi Mestdagh
 */
data class ApiMediumResponse(
    @field:SerializedName("naam") val naam: String ,
    @field:SerializedName("beschrijving") val beschrijving: String,
    @field:SerializedName("mediumId") val mediumId: Int,
    @field:SerializedName("url") val url:String,
    @field:SerializedName("mediumType") val mediumType: Int
)
