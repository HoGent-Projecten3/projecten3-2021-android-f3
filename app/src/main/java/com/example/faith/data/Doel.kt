package com.example.faith.data


import android.util.Log

class Doel(
    private var naam: String,
    private var checked: Boolean,
    private var collapsed: Boolean

) : IDoel {

    private val stappen: MutableList<IDoel> = mutableListOf<IDoel>()

    override fun getNaam(): String {
        return naam
    }

    override fun setNaam(naam: String) {
        this.naam = naam
    }

    override fun addStap(stap: IDoel){
        stappen.add(stap)
    }

    override fun getStappen(): List<IDoel>{
        return stappen
    }

    override fun isChecked(): Boolean {
        Log.i("Doel","Checking children")
        for (stap in stappen){
            if(!stap.isChecked())
                checked = false
        }
        checked = true
        return checked
    }

    override fun setChecked(flag: Boolean) {
        throw Exception("Can't set checked on doel")
    }

    override fun isCollapsed(): Boolean {
        return collapsed
    }

    override fun setCollapsed(flag: Boolean) {
        this.collapsed = flag
    }

    fun getDoelenDTO(): List<DoelDTO>{
        val stappenDTO = mutableListOf<DoelDTO>()
        for (stap: IDoel in stappen){
            if(stap is Doel){
                stappenDTO.add(DoelDTO(stap as Doel))
            }else if(stap is Stap){
                stappenDTO.add(DoelDTO(stap as Stap))
            }
        }
        return stappenDTO
    }
}