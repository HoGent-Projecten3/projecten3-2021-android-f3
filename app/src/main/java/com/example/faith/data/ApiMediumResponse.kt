package com.example.faith.data

import com.google.gson.annotations.SerializedName
/**
 * @author Remi Mestdagh
 */
data class ApiMediumResponse(
    @field:SerializedName("naam") val Naam: String ,
    @field:SerializedName("beschrijving") val Beschrijving: String,
    @field:SerializedName("mediumId") val MediumId: Int,
    @field:SerializedName("url") val Url:String
)
