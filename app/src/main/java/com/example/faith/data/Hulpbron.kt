package com.example.faith.data

import com.google.gson.annotations.SerializedName
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.*

@Entity(tableName = "hulpbronnen")
data class Hulpbron (
        @PrimaryKey @ColumnInfo(name = "id")
        val hulpbronId: Int,
        val titel: String,
        val inhoud: String?,
        val url: String?,
        val telefoonnummer: String?,
        val emailadres: String?,
        val chatUrl: String?,
        val datum: String?
        ) {
        override fun toString() = titel

}
