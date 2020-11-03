package com.example.faith.data


interface IDoel{
    fun getNaam(): String
    fun setNaam(naam: String)
    fun addStap(stap: IDoel)
    fun getStappen(): List<IDoel>
    fun isChecked(): Boolean
    fun setChecked(flag: Boolean)
    fun isCollapsed(): Boolean
    fun setCollapsed(flag: Boolean)
}