package com.example.faith.data

import com.google.gson.annotations.SerializedName
import java.time.DateTimeException
import java.util.*

data class ApiHulpbron(
        @field:SerializedName("hulpbronId") val hulpbronId:Int,
        @field:SerializedName("titel") val titel:String,
        @field:SerializedName("inhoud") val inhoud:String,
        @field:SerializedName("url") val url:String,
        @field:SerializedName("telefoonnummer") val telefoonnummer:String,
        @field:SerializedName("emailadres") val emailadres:String,
        @field:SerializedName("chatUrl") val chatUrl:String,
        @field:SerializedName("datum") val datum: String
)