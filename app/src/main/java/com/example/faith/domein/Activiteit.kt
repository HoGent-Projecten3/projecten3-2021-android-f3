package com.example.faith.domein

import java.util.*

class Activiteit (activiteitId: Int,startDatum: Date, eindDatum: Date, gebruiker: Gebruiker, tekst : String){

    //PARAMETERS_________________________________________________
    var activiteitId = activiteitId
        set(value){field=value}
        get(){return field}
    var startDatum = startDatum
        set(value){field=value}
        get(){return field}
    var eindDatum = eindDatum
        set(value){field=value}
        get(){return field}
    var gebruiker = gebruiker
        set(value){field=value}
        get(){return field}
    var tekst = tekst
        set(value){field=value}
        get(){return field}
    //CONSTRUCTORS_______________________________________________


    init{

    }
    //METHODS___________________________________________________

}