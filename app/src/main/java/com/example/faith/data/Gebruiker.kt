package com.example.faith.data

import com.google.gson.annotations.SerializedName

data class Gebruiker(
    @SerializedName("voornaam")
    val voornaam: String?,
    @SerializedName("achternaam")
    val achternaam: String?,
    @SerializedName("email")
    val email: String?
)
