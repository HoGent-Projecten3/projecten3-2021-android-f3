package com.example.faith.data

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("authToken")
    var authToken: String,

)
