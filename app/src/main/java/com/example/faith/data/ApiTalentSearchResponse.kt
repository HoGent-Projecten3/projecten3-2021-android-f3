package com.example.faith.data

import com.google.gson.annotations.SerializedName

/**
 * @author Arne De Schrijver
 */

data class ApiTalentSearchResponse (
    @field: SerializedName("resultaten") val resultaten: List<ApiTalent>,
    @field: SerializedName("aantal_paginas") val aantal_paginas: Int
)