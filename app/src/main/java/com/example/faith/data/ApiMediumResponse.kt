package com.example.faith.data

import com.google.gson.annotations.SerializedName

data class ApiMediumResponse(@field:SerializedName("results") val results: ApiPhoto,
                             @field:SerializedName("total_pages") val totalPages: Int)