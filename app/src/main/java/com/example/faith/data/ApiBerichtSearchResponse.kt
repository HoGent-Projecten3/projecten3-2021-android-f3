package com.example.faith.data

import com.google.gson.annotations.SerializedName

/**
 * @author Jef Seys
 */
data class ApiBerichtSearchResponse(
    @field:SerializedName("berichten") val berichten: List<Bericht>,
    @field:SerializedName("totDatum") val totDatum: String,
    @field:SerializedName("aantalPaginas") val aantal: Int
)
