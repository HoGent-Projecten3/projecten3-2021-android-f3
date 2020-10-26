package com.example.faith.domein

import java.util.*

class Prestatie (auteur : String, inhoud: String, type: Int, itemType : ItemType, date: Date) : Item(auteur, inhoud, type, itemType) {

    //PARAMETERS_________________________________________________
    var date : Date = date
        set(value) {    field=value }
        get(){return field}
    //CONSTRUCTORS_______________________________________________

    //METHODS___________________________________________________
}