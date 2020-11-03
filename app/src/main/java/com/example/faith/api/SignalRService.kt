package com.example.faith.api

import android.util.Log
import com.example.faith.ChatFragment
import com.smartarmenia.dotnetcoresignalrclientjava.HubConnection
import com.smartarmenia.dotnetcoresignalrclientjava.HubEventListener
import com.smartarmenia.dotnetcoresignalrclientjava.WebSocketHubConnectionP2
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import javax.inject.Singleton

@Singleton
class SignalRService {

    private var sessionToken: String? = ""
    private lateinit var chatFragment: ChatFragment

    private lateinit var connection: HubConnection

    fun setSessionToken(sessionToken: String?){
        this.sessionToken = sessionToken
    }

    fun start(email: String?, chatFragment: ChatFragment){
        this.chatFragment = chatFragment
        connection = WebSocketHubConnectionP2("https://192.168.2.102:45456/connectionHub", sessionToken)
        GlobalScope.launch {
            connection.connect().wait()
        }
        init(email)
    }
    fun stop(){
        connection.disconnect()
    }
    fun init(email: String?){
        connection.invoke("Join", email)
        connection.subscribeToEvent("OntvangBericht", HubEventListener { message -> Log.i("ontvangen", message.toString()) })
    }
}