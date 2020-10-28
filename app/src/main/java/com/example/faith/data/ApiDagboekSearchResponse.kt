package com.example.faith.data

import com.google.gson.annotations.SerializedName

data class ApiDagboekSearchResponse (@field:SerializedName("results") val results: List<ApiDagboek>,
    @field:SerializedName("total_pages") val totalPages: Int)
