package com.example.faith.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "doelen")
data class Doel(
    @PrimaryKey
    @SerializedName("inhoud")
    @ColumnInfo(name = "inhoud") @NonNull
    var inhoud: String,
    @SerializedName("checked")
    @ColumnInfo(name = "checked") @NonNull
    var checked: Boolean,
    @SerializedName("collapsed")
    @ColumnInfo(name = "collapsed") @NonNull
    var collapsed: Boolean,
    @SerializedName("stappen")
    @Ignore
    var stappen: MutableList<Doel>
) {

    val aantalStappen: Int
        get() {
            if (stappen.isNullOrEmpty()) return 0
            return stappen.size
        }

    constructor(inhoud: String, checked: Boolean, collapsed: Boolean) :
        this(inhoud, checked, collapsed, mutableListOf<Doel>()) {}

    fun verwijderDoel(doel: Doel) {
        if (!stappen.isNullOrEmpty()) {
            if (!stappen.remove(doel)) {
                for (stap: Doel in stappen) {
                    stap.verwijderDoel(doel)
                }
            }
        }
    }

    fun addStap(stap: Doel) {
        if (stappen.isNullOrEmpty()) {
            stappen = mutableListOf<Doel>()
        }
        stappen.add(stap)
    }

    fun getDoel(inhoud: String): Doel? {
        if (this.inhoud.equals(inhoud)) return this
        if (!stappen.isNullOrEmpty()) {
            for (doel: Doel in stappen) {
                val target = doel.getDoel(inhoud)
                if (target != null) return target
            }
        }
        return null
    }
}

/*
@Entity(tableName = "doel_entity_table")
data class DoelEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "doelId") @NonNull
    var doelId: Long,
    @ColumnInfo(name = "inhoud") @NonNull
    var inhoud: String,
    @ColumnInfo(name = "checked") @NonNull
    var checked: Boolean,
    @ColumnInfo(name = "collapsed") @NonNull
    var collapsed: Boolean,
    @ColumnInfo(name = "parentId")
    var parentId: Long
)

@Entity(tableName = "doelen")
data class DoelWithStappenEntity(
    @Embedded
    val parent: DoelEntity,
    @Relation(
        parentColumn = "doelId",
        entityColumn = "parentId"
    )
    val stappen: List<DoelEntity>
)*/
