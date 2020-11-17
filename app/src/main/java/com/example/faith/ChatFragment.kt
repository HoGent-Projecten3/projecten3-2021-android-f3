package com.example.faith

import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.faith.adapters.ReceiveMessageItem
import com.example.faith.adapters.SendMessageItem
import com.example.faith.api.SignalRService
import com.example.faith.data.ApiBerichtSearchResponse
import com.example.faith.data.BerichtXML
import com.example.faith.data.Gebruiker
import com.example.faith.databinding.FragmentChatBinding
import com.example.faith.viewmodels.ChatViewModel
import com.jakewharton.threetenabp.AndroidThreeTen
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.coroutines.Job
import org.threeten.bp.LocalDateTime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * @author Jef Seys
 */
@AndroidEntryPoint
class ChatFragment : Fragment() {
    @Inject lateinit var signalRService: SignalRService
    private val viewModel: ChatViewModel by viewModels()
    private var searchJob: Job? = null
    private val messageAdapter = GroupAdapter<GroupieViewHolder>()

    // Attributen voor gebruiker in chat
    private var mijnEmail = ""
    private var andereEmail = ""
    private var mijnNaam = ""
    private var andereNaam = ""

    // Aantal berichten in recyclerview om naar beneden te kunnen scrollen
    private var positieScroll = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatBinding.inflate(inflater, container, false)
        context ?: return binding.root
        // binding.messagesList.adapter = adapter
        binding.messagesList.adapter = messageAdapter

        // Eigen attributen initialiseren
        getMijnEmail()

        // Recyclerview opvullen met berichten
        getBerichten2()

        // Tijdzone instellen
        AndroidThreeTen.init(context)

        setHasOptionsMenu(true)
        signalRService?.start(mijnEmail, this)

        binding.btSendMessage.setOnClickListener {
            verstuurBericht()
        }
        signalRService?.getConnection().on(
            "OntvangBericht",
            { message: String ->
                addNewMessages(message)
            },
            String::class.java
        )
        binding.messagesList.scrollToPosition(positieScroll - 1)
        return binding.root
    }

    /***
     * SignalR starten en luisteren naar OntvangBericht
     */
    fun initSignalR() {
        signalRService?.start(mijnEmail, this)
        signalRService?.getConnection().on(
            "OntvangBericht",
            { message: String ->
                addNewMessages(message)
            },
            String::class.java
        )
    }

    /***
     * Bericht versturen via signalR en sturen aan backend
     */
    private fun verstuurBericht() {
        initSignalR()
        signalRService.send(txfEditBericht.text.toString(), andereEmail)
        var bericht = BerichtXML(mijnEmail, andereEmail, mijnNaam, andereNaam, txfEditBericht.text.toString(), LocalDateTime.now())
        messageAdapter.add(SendMessageItem(bericht))

        var call: Call<Message>? = viewModel.verstuurBericht(mijnEmail, andereEmail, mijnNaam, andereNaam, txfEditBericht.text.toString())
        call!!.enqueue(
            object : Callback<Message?> {
                override fun onFailure(call: Call<Message?>, t: Throwable) {}
                override fun onResponse(call: Call<Message?>, response: retrofit2.Response<Message?>) {
                }
            }
        )
        viewModel.verstuurBericht(mijnEmail, andereEmail, mijnNaam, andereNaam, txfEditBericht.text.toString())

        txfEditBericht.setText("")
        positieScroll++
        messages_list.scrollToPosition(positieScroll - 1)
    }

    /***
     * Bericht toevoegen aan recyclerview
     */
    private fun addNewMessages(message: String) {
        var bericht = BerichtXML(andereEmail, mijnEmail, andereNaam, mijnNaam, message, LocalDateTime.now())
        messageAdapter.add(ReceiveMessageItem(bericht))
        positieScroll++
        messages_list.scrollToPosition(positieScroll - 1)
    }

    /***
     * Berichten laden in recyclerview
     */
    private fun getBerichten2() {
        viewModel.geefBerichten2().enqueue(

            object : Callback<ApiBerichtSearchResponse?> {
                override fun onResponse(
                    call: Call<ApiBerichtSearchResponse?>,
                    response: Response<ApiBerichtSearchResponse?>
                ) {
                    var bericht = response.body()?.results
                    bericht?.forEach {
                        if (it.verstuurderEmail.equals(mijnEmail)) {
                            andereEmail = it.ontvangerEmail
                            andereNaam = it.ontvangerNaam
                        } else {
                            andereEmail = it.verstuurderEmail
                            andereNaam = it.verstuurderNaam
                        }
                        if (it.verstuurderEmail.equals(mijnEmail)) {
                            messageAdapter.add(SendMessageItem(BerichtXML(it.verstuurderEmail, it.ontvangerEmail, it.verstuurderNaam, it.ontvangerNaam, it.text, LocalDateTime.parse(it.datum))))
                        } else {
                            messageAdapter.add(ReceiveMessageItem(BerichtXML(it.verstuurderEmail, it.ontvangerEmail, it.verstuurderNaam, it.ontvangerNaam, it.text, LocalDateTime.parse(it.datum))))
                        }
                        positieScroll++
                    }
                }

                override fun onFailure(call: Call<ApiBerichtSearchResponse?>, t: Throwable) {
                    Log.e("Error", t.toString())
                }
            }
        )
    }

    /***
     * Email en naam instellen van eigen gebruiker
     */
    private fun getMijnEmail() {

        viewModel.getGebruiker().enqueue(

            object : Callback<Gebruiker?> {
                override fun onResponse(call: Call<Gebruiker?>, response: Response<Gebruiker?>) {
                    mijnEmail = response.body()?.email.toString()
                    mijnNaam = response.body()?.voornaam + " " + response.body()?.achternaam
                }

                override fun onFailure(call: Call<Gebruiker?>, t: Throwable) {
                    Log.v("Error", t.toString())
                }
            }
        )
    }
}
