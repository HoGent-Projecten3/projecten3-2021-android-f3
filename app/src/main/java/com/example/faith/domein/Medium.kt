package com.example.faith.domein

import java.util.*

abstract class Medium (auteur: String, date: Date, naam: String, url: String){
    //PARAMETERS_________________________________________________
    var type : MediumType? = null
        set(value){ field=value}
        get(){ return field }
    var auteur = auteur
        set(value){ field=value}
        get(){ return field }
    var date = auteur
        set(value){ field=value}
        get(){ return field }
    var naam = auteur
        set(value){ field=value}
        get(){ return field }
    var url = auteur
        set(value){ field=value}
        get(){ return field }
    var mediumId : Int? = null
        set(value){ field=value}
        get(){ return field }
    //CONSTRUCTORS_______________________________________________


    //METHODS___________________________________________________
    /**
     * TODO
     */
    fun open()
    {

    }

}