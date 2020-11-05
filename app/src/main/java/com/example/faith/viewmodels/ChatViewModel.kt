package com.example.faith.viewmodels

import android.os.Message
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.faith.data.*
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Call

class ChatViewModel @ViewModelInject constructor(
    private val gebruikerRepository: GebruikerRepository,
    private val berichtRepository: BerichtRepository
)  : ViewModel() {

    fun getGebruiker(): Call<Gebruiker> {
        return gebruikerRepository.getGebruiker()
    }

    fun verstuurBericht(mijnEmail:String,andereEmail:String, mijnNaam:String, andereNaam:String,text:String): Call<Message> {
        return berichtRepository.verstuurBericht(mijnEmail,andereEmail, mijnNaam, andereNaam,text)
    }

    fun geefBerichten():Flow<PagingData<ApiBericht>>{
            return berichtRepository.getBerichten().cachedIn(viewModelScope)
    }
     fun geefBerichten2(): Call<ApiBerichtSearchResponse>{
        return berichtRepository.getBerichten2()
    }
}