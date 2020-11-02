package com.example.faith.viewmodels

import android.os.Message
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.faith.data.Bericht
import com.example.faith.data.BerichtRepository
import com.example.faith.data.GebruikerRepository
import retrofit2.Call

class ChatViewModel @ViewModelInject constructor(
    private val gebruikerRepository: GebruikerRepository,
    private val berichtRepository: BerichtRepository
)  : ViewModel() {
    // TODO: Implement the ViewModel

    fun getGebruikerRepository(): GebruikerRepository {
        return gebruikerRepository
    }

    fun verstuurBericht(bericht: Bericht): Call<Message> {
        return berichtRepository.verstuurBericht(bericht)
    }

    fun geefBerichten():List<Bericht>{
        return berichtRepository.getBerichten()
    }
}