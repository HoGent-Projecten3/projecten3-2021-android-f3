package com.example.faith.data

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Doel(
    @SerializedName("inhoud")
    @PrimaryKey @NonNull
    var inhoud: String,
    @SerializedName("checked") @NonNull
    var checked: Boolean,
    @SerializedName("collapsed") @NonNull
    var collapsed: Boolean,
    @SerializedName("stappen")
    @Embedded
    var stappen: MutableList<Doel>
){

    fun verwijderDoel(doel: Doel) {
        if(!stappen.isNullOrEmpty()) {
            if (!stappen.remove(doel)) {
                for (stap: Doel in stappen) {
                    stap.verwijderDoel(doel)
                }
            }
        }
    }

    fun addStap(stap: Doel){
        if(stappen.isNullOrEmpty()){
            stappen = mutableListOf()
        }
        stappen.add(stap)
    }
}
