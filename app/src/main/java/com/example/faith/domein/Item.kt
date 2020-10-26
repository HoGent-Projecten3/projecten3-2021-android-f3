package com.example.faith.domein

open abstract class Item (auteur : String, var inhoud: String, var type: Int, var itemType : ItemType){

    var auteur : String = auteur
        set(value) {
            field = value;
        }
        get() = field
    //PARAMETERS_________________________________________________

    //CONSTRUCTORS_______________________________________________

    init{

    }
    //METHOD

}