package com.example.faith.domein

class Begeleider(achternaam: String, email: String, voornaam: String) : Gebruiker(achternaam, email, voornaam){
    //PARAMETERS_________________________________________________
    var clients : MutableList<Client> = mutableListOf<Client>()

    //CONSTRUCTORS_______________________________________________


    init{

    }
    //METHODS___________________________________________________

    /**
     * Adds a client to the mutableList of clients
     */
    fun addClient(client:Client)
    {
        this.clients.add(client)
    }

    /**
     * Remove a client from the mutableList of clients
     */
    fun verwijderClient(client:Client)
    {
        this.clients.remove(client)
    }



}