package com.example.faith.data

import com.google.gson.annotations.SerializedName
/**
 * @author Remi Mestdagh
 */
data class Dagboek(
    @field:SerializedName("naam") val naam: String,
    @field:SerializedName("mediumId") val mediumId: Int,
    @field:SerializedName("beschrijving") val beschrijving: String
)
