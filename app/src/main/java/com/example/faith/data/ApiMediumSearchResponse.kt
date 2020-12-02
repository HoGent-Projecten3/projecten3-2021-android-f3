package com.example.faith.data

import com.google.gson.annotations.SerializedName
/**
 * @author Remi Mestdagh
 */
data class ApiMediumSearchResponse(
    @field:SerializedName("results") val results: List<ApiMediumResponse>,
    @field:SerializedName("total_pages") val totalPages: Int
)
