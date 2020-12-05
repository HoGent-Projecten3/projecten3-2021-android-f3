package com.example.faith.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Arne De Schrijver
 */

@Entity(tableName = "Items")
data class Talent (
    @PrimaryKey @ColumnInfo(name = "id") val talentId: Int,
    val inhoud: String

    ) {

    override fun toString() = inhoud
}

