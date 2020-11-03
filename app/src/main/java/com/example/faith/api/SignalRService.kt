package com.example.faith.api

import com.example.faith.ChatFragment
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
class SignalRService {

    private var sessionToken: String? = ""
    private lateinit var chatFragment: ChatFragment
    private lateinit var hubConnection: HubConnection

    fun setSessionToken(sessionToken: String?){
        this.sessionToken = sessionToken
    }

    fun start(email: String?, chatFragment: ChatFragment){
        this.chatFragment = chatFragment
        hubConnection =  HubConnectionBuilder.create("http://192.168.1.37:45455/connectionHub").withAccessTokenProvider(
            Single.defer {
                Single.just(
                    "Bearer $sessionToken"
                )
            }

        ).build()
        hubConnection.start().blockingAwait()
        init(email)
        send("kaas")
        hubConnection.on(
            "OntvangBericht",
            { message: String -> println("New Message: $message") },
            String::class.java
        )
    }
    fun stop(){
        hubConnection.stop()
    }
    fun print(){

    }
    fun init(email: String?){

        hubConnection.send("Join", "jef.seys.y0431@student.hogent.be")

    }
    fun send(message: String){
        hubConnection.send("Verstuur", "jef.seys.y0431@student.hogent.be", "kaas")
    }
}