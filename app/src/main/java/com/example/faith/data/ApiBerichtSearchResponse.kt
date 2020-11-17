package com.example.faith.data

import com.google.gson.annotations.SerializedName

/**
 * @author Jef Seys
 */
data class ApiBerichtSearchResponse(
    @field:SerializedName("results") val results: List<ApiBericht>,
    @field:SerializedName("total_pages") val totalPages: Int
)
