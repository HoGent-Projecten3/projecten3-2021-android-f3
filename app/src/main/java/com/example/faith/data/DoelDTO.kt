package com.example.faith.data

import com.google.gson.annotations.SerializedName

public class DoelDTO(
    @SerializedName("inhoud")
    var inhoud: String?,
    @SerializedName("checked")
    var checked: Boolean?,
    @SerializedName("collapsed")
    var collapsed: Boolean?,
    @SerializedName("stappen")
    var stappen: List<DoelDTO>?
){

    constructor(doel: Doel): this(doel.getNaam(), doel.isChecked(), doel.isCollapsed(), doel.getDoelenDTO())

    constructor(stap: Stap): this(stap.getNaam(), stap.isChecked(), null, null)

    fun getDoel(): IDoel{
        if(stappen != null) {
            var doel = Doel(inhoud!!, checked!!, collapsed!!)
            for (stap: DoelDTO in stappen!!) {
                doel.addStap( stap.getDoel())
            }
            return doel
        }else{
            return Stap(inhoud!!, checked!!)
        }
    }
}