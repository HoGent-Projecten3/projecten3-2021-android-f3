package com.example.faith.domein

open class Gebruiker( achternaam : String,  email : String,  voornaam : String) {

    //PARAMETERS_________________________________________________
    var chats : MutableList<Chat> = mutableListOf<Chat>()
    var achternaam : String = achternaam
        set(value) {                                                //SETTER
            field = value
        }
        get() {
            return field
        }
    var email: String = email
        set(value) {                                                //SETTER
            field = value
        }
        get() {
            return field
        }
    var voornaam: String = voornaam
        set(value) {                                                //SETTER
            field = value
        }
        get() {
            return field
        }

    //CONSTRUCTORS_______________________________________________



    init{

    }

    //METHODS___________________________________________________

    /**
     * TODO
     */
    fun addChat()
    {
    }
    /**
     * TODO
     */
    fun verwijderChat()
    {
    }

    /**
     * TODO
     */
    fun planActiviteit()
    {
    }
    /**
     * TODO
     */
    fun verwijderActiviteit()
    {
    }

    /**
     * TODO
     */
    fun verstuurBericht()
    {
    }
    /**
     * TODO
     */
    fun verwijderBericht()
    {
    }





}