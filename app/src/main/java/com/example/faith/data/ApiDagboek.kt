package com.example.faith.data

import com.google.gson.annotations.SerializedName

data class ApiDagboek(
    @field:SerializedName("naam") val naam:String,
    @field:SerializedName("mediumId") val mediumId:Int,
    @field:SerializedName("beschrijving") val beschrijving:String
    )