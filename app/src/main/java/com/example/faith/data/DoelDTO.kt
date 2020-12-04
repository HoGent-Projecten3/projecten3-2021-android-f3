package com.example.faith.data

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "doelen")
class DoelDTO(
    @SerializedName("inhoud")
    @PrimaryKey @NonNull
    var inhoud: String,
    @SerializedName("checked")
    var checked: Boolean?,
    @SerializedName("collapsed")
    var collapsed: Boolean?,
    @SerializedName("stappen")
    @Embedded
    var stappen: List<DoelDTO>?
) {

    constructor(doel: Doel) : this(doel.getNaam(), doel.isChecked(), doel.isCollapsed(), doel.getDoelenDTO())

    constructor(stap: Stap) : this(stap.getNaam(), stap.isChecked(), null, null)

    fun getDoel(): IDoel {
        if (stappen != null && collapsed != null) {
            var doel = Doel(inhoud!!, checked!!, collapsed!!)
            for (stap: DoelDTO in stappen!!) {
                doel.addStap(stap.getDoel())
            }
            return doel
        } else {
            return Stap(inhoud!!, checked!!)
        }
    }

    override fun toString(): String {
        var print: String = "$inhoud, $checked, $collapsed;\n"
        if (stappen != null) {
            for (stap: DoelDTO in stappen!!) {
                print += stap.toString()
            }
        }
        return print
    }
}
