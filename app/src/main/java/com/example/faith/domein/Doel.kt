package com.example.faith.domein

class Doel (auteur : String, inhoud: String, type: Int, itemType : ItemType) : Item(auteur, inhoud, type, itemType) {

    //PARAMETERS_________________________________________________
    var stappen : MutableList<Stap> = mutableListOf()
        set(value) {    field=value }
        get(){return field}

    // PRIMARY CONSTRUCTOR - logic block_______________________________________________________________________________________________

    init{

    }
    // SECONDARY CONSTRUCTOR(S)
    constructor(auteur : String, inhoud: String, type: Int, itemType : ItemType, stappen : MutableList<Stap>) : this(auteur, inhoud, type, itemType){
        this.stappen = stappen
    }

    //METHODS___________________________________________________
    fun addStap(stap: Stap)
    {

    }
}