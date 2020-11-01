package com.example.faith.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * @author Remi Mestdagh
 */
@Entity(tableName = "media")
data class Medium(
    @PrimaryKey @ColumnInfo(name = "id") val mediumId: Int,
    val naam: String,
    val beschrijving: String,
    val url: String = ""
) {

    override fun toString() = naam
}
