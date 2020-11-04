package com.example.faith.viewmodels

import android.os.Message
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.faith.data.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
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

    fun geefBerichten():Flow<PagingData<ApiBericht>>{
            return berichtRepository.getBerichten().cachedIn(viewModelScope)
    }
}