package com.example.faith.data

import com.google.gson.annotations.SerializedName

data class ApiSearchResponse(
    @field:SerializedName("results") val results: List<ApiPhoto>,
    @field:SerializedName("total_pages") val totalPages: Int
)
