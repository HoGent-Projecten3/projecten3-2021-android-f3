package com.example.faith.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * @author Arne De Schrijver
 */

@Entity(tableName = "Items")
data class Talent(
    @PrimaryKey @ColumnInfo(name = "id") @field: SerializedName("trofeeId") val talentId: Int,
    @field: SerializedName("inhoud") val inhoud: String,
    @field: SerializedName("type") val type: Int,
    //@field: SerializedName("auteur") val auteur: String
) {

    override fun toString() = inhoud
}

