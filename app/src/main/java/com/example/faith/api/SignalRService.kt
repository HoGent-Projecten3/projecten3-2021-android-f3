package com.example.faith.api

import com.example.faith.ChatFragment
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import io.reactivex.Single
import javax.inject.Singleton

/**
 * @author Remi Mestdagh & Jef Seys
 */
@Singleton
class SignalRService {

    private var sessionToken: String? = ""
    private lateinit var chatFragment: ChatFragment
    private lateinit var hubConnection: HubConnection

    fun setSessionToken(sessionToken: String?) {
        this.sessionToken = sessionToken
    }

    fun start(email: String?, chatFragment: ChatFragment) {
        this.chatFragment = chatFragment
        hubConnection = HubConnectionBuilder.create("https://f3backend-dev-as.azurewebsites.net/connectionHub/")
            .withAccessTokenProvider(
                Single.defer {
                    Single.just(
                        "Bearer $sessionToken"
                    )
                }

            ).build()
        hubConnection.keepAliveInterval = 3600000
        hubConnection.start().blockingAwait()
        init(email)
    }

    fun stop() {
        hubConnection.stop()
    }

    fun getConnection(): HubConnection {
        return hubConnection
    }

    fun init(email: String?) {
        hubConnection.send("Join", email)
    }

    fun send(message: String, email: String) {
        hubConnection.send("Verstuur", email, message)
    }
}
