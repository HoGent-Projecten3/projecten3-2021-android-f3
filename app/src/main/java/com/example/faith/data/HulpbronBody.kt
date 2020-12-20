package com.example.faith.data

import com.google.gson.annotations.SerializedName

class HulpbronBody(
    @SerializedName("titel") val titel: String,
    @SerializedName("inhoud") val inhoud: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("telefoonnummer") val telefoonnummer: String?,
    @SerializedName("emailadres") val emailadres: String?,
    @SerializedName("chatUrl") val chatUrl: String?
)
