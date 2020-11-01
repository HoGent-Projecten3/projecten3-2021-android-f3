package com.example.faith.data

import com.google.gson.annotations.SerializedName

data class Login(
    @SerializedName("email") val email: String?,
    @SerializedName("password") val wachtwoord: String?
)
