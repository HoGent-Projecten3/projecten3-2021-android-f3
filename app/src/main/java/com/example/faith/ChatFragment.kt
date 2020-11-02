package com.example.faith

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.faith.adapters.BerichtAdapter
import com.example.faith.data.Bericht
import com.example.faith.data.GebruikerRepository
import com.example.faith.databinding.ChatFragmentBinding
import com.example.faith.databinding.FragmentCinemaBinding
import com.example.faith.viewmodels.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.chat_fragment.*
import java.util.*

@AndroidEntryPoint
class ChatFragment : Fragment() {
    private lateinit var adapter: BerichtAdapter
    private val viewModel: ChatViewModel by viewModels()
    private lateinit var gebruikerRepository: GebruikerRepository

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

        var berichten = viewModel.geefBerichten()

        for(i in berichten){
            adapter.addMessage(i)
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

        return binding.root
    }


}