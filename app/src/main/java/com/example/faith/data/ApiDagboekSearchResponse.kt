package com.example.faith.data

import com.google.gson.annotations.SerializedName
/**
 * @author Remi Mestdagh
 */
data class ApiDagboekSearchResponse(
    @field:SerializedName("results") val results: List<ApiDagboek>,
    @field:SerializedName("total_pages") val totalPages: Int
)
