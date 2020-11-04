package com.example.faith

import android.icu.text.DateFormat
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.faith.adapters.BerichtAdapter
import com.example.faith.api.SignalRService
import com.example.faith.data.Bericht
import com.example.faith.data.GebruikerRepository
import com.example.faith.databinding.ChatFragmentBinding
import com.example.faith.viewmodels.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime;
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ChatFragment constructor() : Fragment() {
    @Inject lateinit var signalRService: SignalRService
    private lateinit var adapter: BerichtAdapter
    private val viewModel: ChatViewModel by viewModels()
    private lateinit var gebruikerRepository: GebruikerRepository
    private var searchJob: Job? = null
    private var berichten: List<Bericht> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<ChatFragmentBinding>(
            inflater,
            R.layout.chat_fragment,
            container,
            false
        )
        gebruikerRepository = viewModel.getGebruikerRepository()
        binding.messageList.layoutManager = LinearLayoutManager(this.context)
        adapter = BerichtAdapter(context, gebruikerRepository)
        gebruikerRepository = viewModel.getGebruikerRepository()
        binding.messageList.adapter = adapter

        //var datum = LocalDateTime.parse("2020-11-04T11:28:13.194849")

         getBerichten()

        if (berichten != null) {
            for (i in berichten) {
                adapter.addMessage(i)
            }
        }
        binding.messageList.scrollToPosition(adapter.itemCount - 1);
        binding.btnSend.setOnClickListener {
            if (binding.txtMessage.text.isNotEmpty()) {
                /*val message = Bericht(
                    gebruikerRepository.getGebruiker().execute().body(),
                    binding.txtMessage.text.toString(),
                    Date()
                )*/

                //val call = viewModel.verstuurBericht(message)
                Log.i("Versuur bericht", binding.txtMessage.text.toString())
            }
        }

        signalRService?.start("joost@kaas.be", this)
        return binding.root
    }

    fun getDateFromatedString(inputDate: String?): String? {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        var date: Date? = null
        try {
            date = simpleDateFormat.parse(inputDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        if (date == null) {
            return ""
        }
        val convetDateFormat = SimpleDateFormat("yyyy-MM-dd")
        return convetDateFormat.format(date)
    }



    private fun getBerichten() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.geefBerichten().collectLatest {
                it.
            }
        }
    }
}