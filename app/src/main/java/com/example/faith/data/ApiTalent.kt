package com.example.faith.data

import com.google.gson.annotations.SerializedName

/**
 * @author Arne De Schrijver
 */

data class ApiTalent (
    @field: SerializedName("talentId") val talentId: Int,
    @field: SerializedName("inhoud") val inhoud: String
)