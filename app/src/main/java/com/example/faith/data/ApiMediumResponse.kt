package com.example.faith.data

import com.google.gson.annotations.SerializedName
/**
 * @author Remi Mestdagh
 */
data class ApiMediumResponse(
    @field:SerializedName("results") val results: ApiPhoto,
    @field:SerializedName("total_pages") val totalPages: Int
)
