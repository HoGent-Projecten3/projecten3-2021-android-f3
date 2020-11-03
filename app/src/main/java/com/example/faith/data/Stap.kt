package com.example.faith.data


class Stap(
    private var naam: String,
    private var checked: Boolean

): IDoel{

    override fun getNaam(): String {
        return naam
    }

    override fun setNaam(naam: String) {
        this.naam = naam
    }

    override fun addStap(stap: IDoel) {
        throw Exception("Can't add step to a step")
    }

    override fun getStappen(): List<IDoel> {
        throw Exception("A step doesn't have steps")
    }

    override fun isChecked(): Boolean {
        return checked
    }

    override fun setChecked(flag: Boolean) {
        this.checked = flag
    }

    override fun isCollapsed(): Boolean {
        throw Exception("Can't collapse a step")
    }

    override fun setCollapsed(flag: Boolean) {
        throw Exception("Can't collapse a step")
    }

}