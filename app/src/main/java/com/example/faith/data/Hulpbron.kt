package com.example.faith.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "hulpbronnen")
data class Hulpbron(
    @PrimaryKey @ColumnInfo(name = "id")
    @field:SerializedName("hulpbronId")
    val hulpbronId: Int,
    @field:SerializedName("titel")
    val titel: String,
    @field:SerializedName("inhoud")
    val inhoud: String,
    @field:SerializedName("url")
    val url: String?,
    @field:SerializedName("telefoonnummer")
    val telefoonnummer: String?,
    @field:SerializedName("emailadres")
    val emailadres: String?,
    @field:SerializedName("chatUrl")
    val chatUrl: String?,
    @field:SerializedName("datum")
    val datum: String?,
    @field:SerializedName("auteurType")
    val auteurType: String,
) {
    override fun toString() = titel
}
