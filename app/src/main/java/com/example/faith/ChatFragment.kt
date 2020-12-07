package com.example.faith

import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.faith.adapters.ReceiveMessageItem
import com.example.faith.adapters.SendMessageItem
import com.example.faith.api.SignalRService
import com.example.faith.data.ApiBerichtSearchResponse
import com.example.faith.data.BerichtXML
import com.example.faith.data.Gebruiker
import com.example.faith.databinding.FragmentChatBinding
import com.example.faith.viewmodels.ChatViewModel
import com.example.faith.viewmodels.LoginViewModel
import com.jakewharton.threetenabp.AndroidThreeTen
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.coroutines.Job
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter.ISO_INSTANT
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

/**
 * @author Jef Seys
 */
@AndroidEntryPoint
class ChatFragment : Fragment() {
    @Inject lateinit var signalRService: SignalRService
    private val viewModel: ChatViewModel by viewModels()
    private val loginViewModel: LoginViewModel by activityViewModels()
    private val messageAdapter = GroupAdapter<GroupieViewHolder>()

    // Attributen voor gebruiker in chat
    private var mijnEmail = ""
    private var andereEmail = ""
    private var mijnNaam = ""
    private var andereNaam = ""

    // Aantal berichten in recyclerview om naar beneden te kunnen scrollen
    private var positieScroll = 0

    // Attributen voor getBerichten
    private var aantal = 20;
    private lateinit var totDatum: LocalDateTime

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatBinding.inflate(inflater, container, false)
        context ?: return binding.root

        binding.messagesList.adapter = messageAdapter

        // Email ingelogde gebruiker instellen
        loginViewModel.gebruikerEmail.observe(
                this.viewLifecycleOwner,
                {
                    mijnEmail = it
                }
        )

        // Tijdzone instellen
        AndroidThreeTen.init(context)
        totDatum = LocalDateTime.now()

        // Recyclerview opvullen met berichten
        getBerichten2()

        setHasOptionsMenu(true)

        binding.btSendMessage.setOnClickListener {
            verstuurBericht()
        }

        binding.messagesList.scrollToPosition(positieScroll - 1)
        mijnEmail = loginViewModel.gebruikerEmail.value.toString()
        initSignalR()
        return binding.root
    }

    /***
     * SignalR starten en luisteren naar OntvangBericht
     */
    fun initSignalR() {
        signalRService?.start(mijnEmail, this)
        signalRService?.getConnection().on(
            "OntvangBericht",
            { text: String, verstuurderEmail: String, chatId: String ->
                addNewMessages(text)
            },
            String::class.java,
                String::class.java,
                String::class.java
        )
    }

    /***
     * Bericht versturen via signalR en sturen aan backend
     */
    private fun verstuurBericht() {
        initSignalR()
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

        txfEditBericht.setText("")
        positieScroll++
        messages_list.scrollToPosition(positieScroll-1)
    }

    /***
     * Bericht toevoegen aan recyclerview
     */
    private fun addNewMessages(message: String) {
        initSignalR()
        var bericht = BerichtXML(andereEmail, mijnEmail, andereNaam, mijnNaam, message, LocalDateTime.now())
        messageAdapter.add(ReceiveMessageItem(bericht))
        positieScroll++
        messages_list.scrollToPosition(positieScroll-1)
    }

    /***
     * Berichten laden in recyclerview
     */
    private fun getBerichten2() {
        viewModel.geefBerichten2(totDatum.toString(), aantal).enqueue(

            object : Callback<ApiBerichtSearchResponse?> {
                override fun onResponse(
                    call: Call<ApiBerichtSearchResponse?>,
                    response: Response<ApiBerichtSearchResponse?>
                ) {
                    var berichtLijst = response.body()?.berichten
                    berichtLijst?.forEach {
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
                    totDatum = LocalDateTime.parse(response.body()?.totDatum);
                    aantal = response.body()?.aantal!!;
                }

                override fun onFailure(call: Call<ApiBerichtSearchResponse?>, t: Throwable) {
                    Log.e("Error", t.toString())
                }
            }
        )
    }
}
