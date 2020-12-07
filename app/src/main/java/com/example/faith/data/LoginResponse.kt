package com.example.faith.data

import com.google.gson.annotations.SerializedName

/**
 * @author Remi Mestdagh
 * POJO om jwt token op te vangen
 */
data class LoginResponse(
    @SerializedName("authToken")
    var authToken: String

)
