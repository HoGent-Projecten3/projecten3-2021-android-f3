package com.example.faith.data

import com.google.gson.annotations.SerializedName

/**
 * @author Arne De Schrijver
 */

data class ApiTalentSearchResponse (
    @field: SerializedName("results") val resultaten: List<Talent>,
    @field: SerializedName("total_pages") val totalPages: Int,
    @field:SerializedName("last") val last: String?,
    @field:SerializedName("next") val next: String?
)