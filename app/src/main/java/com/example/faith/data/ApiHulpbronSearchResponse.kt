package com.example.faith.data

import com.google.gson.annotations.SerializedName

data class ApiHulpbronSearchResponse(
    @field:SerializedName("results") val results: List<Hulpbron>,
    @field:SerializedName("total_pages") val totalPages: Int,
    @field:SerializedName("last") val last: String?,
    @field:SerializedName("next") val next: String?
) {

}