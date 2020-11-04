package com.example.faith

import android.icu.text.SimpleDateFormat
import android.net.ParseException
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.faith.adapters.ReceiveMessageItem
import com.example.faith.adapters.SendMessageItem
import com.example.faith.api.SignalRService
import com.example.faith.data.ApiBericht
import com.example.faith.data.ApiBerichtSearchResponse
import com.example.faith.data.Bericht
import com.example.faith.data.GebruikerRepository
import com.example.faith.databinding.FragmentChatBinding
import com.example.faith.viewmodels.ChatViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ChatFragment : Fragment() {
    @Inject lateinit var signalRService: SignalRService
    private val viewModel: ChatViewModel by viewModels()
    private var searchJob: Job? = null
    private val messageAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatBinding.inflate(inflater, container, false)
        context ?: return binding.root
        // binding.messagesList.adapter = adapter
        binding.messagesList.adapter = messageAdapter
        getBerichten2()

        setHasOptionsMenu(true)
        signalRService?.start("jef.seys.y0431@student.hogent.be", this)

        binding.btSendMessage.setOnClickListener {
            verstuurBericht()
        }
        signalRService?.getConnection().on("OntvangBericht",
            { message: String ->
                addNewMessages(message)
            },
            String::class.java
        )
        return binding.root
    }
    fun initSignalR(){
        signalRService?.start("jef.seys.y0431@student.hogent.be", this)
        signalRService?.getConnection().on("OntvangBericht",
            { message: String ->
                addNewMessages(message)
            },
            String::class.java
        )
    }
    private fun verstuurBericht(){
        initSignalR()
        signalRService.send(txfEditBericht.text.toString(), "jef.seys.y0431@student.hogent.be")
        var bericht = ApiBericht(0,"jef.seys.y0431@student.hogent.be","jef.seys.y0431@student.hogent.be",txfEditBericht.text.toString(), LocalDateTime.now().toString())
        messageAdapter.add(SendMessageItem(bericht))
    }
    private fun addNewMessages(message:String){
        var bericht = ApiBericht(0,"jef.seys.y0431@student.hogent.be","jef.seys.y0431@student.hogent.be",message, LocalDateTime.now().toString())
        messageAdapter.add(ReceiveMessageItem(bericht))
    }
    private fun getBerichten2(){
        viewModel.geefBerichten2().enqueue(

            object : Callback<ApiBerichtSearchResponse?> {
                override fun onResponse(
                    call: Call<ApiBerichtSearchResponse?>,
                    response: Response<ApiBerichtSearchResponse?>
                ) {
                    var bericht = response.body()?.results
                    bericht?.forEach {
                        if(it.verstuurder.equals("jef.seys.y0431@student.hogent.be")){
                            messageAdapter.add(SendMessageItem(it))
                        } else{
                            messageAdapter.add(ReceiveMessageItem(it))

                        }
                    }
                }

                override fun onFailure(call: Call<ApiBerichtSearchResponse?>, t: Throwable) {
                    Log.e("Error", t.toString())
                }
            }
        )
    }
}