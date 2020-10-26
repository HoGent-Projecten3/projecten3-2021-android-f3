package com.example.faith.domein

class Chat (var chatId: Int){

    //PARAMETERS_________________________________________________
    var berichten : MutableList<ChatBericht> = mutableListOf<ChatBericht>()
    var deelnemers : MutableList<Gebruiker> = mutableListOf<Gebruiker>()
    //CONSTRUCTORS_______________________________________________


    init{

    }
    //METHODS___________________________________________________

    /**
     * Voegt een chatbericht toe aan de chatberichten van de chat
     */
    fun addChatBericht(bericht: ChatBericht)
    {
        this.berichten.add(bericht)
    }
    /**
     * Voegt een deelnemer toe aan de deelnemers van de chat
     */
    fun addDeelnemer(deelnemer: Gebruiker)
    {
        this.deelnemers.add(deelnemer)
    }
    /**
     * TODO
     */
    fun verwijderChatBericht()
    {

    }
}