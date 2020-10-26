package com.example.faith.domein

class Client(achternaam: String, email: String, voornaam: String) : Gebruiker(achternaam, email, voornaam){

//PARAMETERS_________________________________________________
    var items : MutableList<Item> = mutableListOf<Item>()
    var media : MutableList<Medium> = mutableListOf<Medium>()
    var begeleider : Begeleider? = null
//CONSTRUCTORS_______________________________________________


    init{

    }
//METHODS___________________________________________________

    /**
     * TODO
     */
    fun addMedium(medium:Medium)
    {
        media.add(medium)
    }
    /**
     * TODO
     */
    fun addVriend()
    {
    }
    /**
     * TODO
     */
    fun rapporteerBericht()
    {
    }
    /**
     * TODO
     */
    fun verwijderMedium()
    {
    }
    /**
     * TODO
     */
    fun verwijderVriend()
    {
    }


}

//PARAMETERS_________________________________________________

//CONSTRUCTORS_______________________________________________

//METHODS___________________________________________________