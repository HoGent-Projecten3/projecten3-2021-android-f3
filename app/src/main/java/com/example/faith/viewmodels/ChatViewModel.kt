package com.example.faith.viewmodels

import android.os.Message
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.faith.data.ApiBericht
import com.example.faith.data.ApiBerichtSearchResponse
import com.example.faith.data.BerichtRepository
import com.example.faith.data.Gebruiker
import com.example.faith.data.GebruikerRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Call

/**
 * @author Jef Seys
 */
class ChatViewModel @ViewModelInject constructor(
    private val gebruikerRepository: GebruikerRepository,
    private val berichtRepository: BerichtRepository
) : ViewModel() {

    fun getGebruiker(): Call<Gebruiker> {
        return gebruikerRepository.getGebruiker()
    }

    fun verstuurBericht(mijnEmail: String, andereEmail: String, mijnNaam: String, andereNaam: String, text: String): Call<Message> {
        return berichtRepository.verstuurBericht(mijnEmail, andereEmail, mijnNaam, andereNaam, text)
    }

    fun geefBerichten(): Flow<PagingData<ApiBericht>> {
        return berichtRepository.getBerichten().cachedIn(viewModelScope)
    }
    fun geefBerichten2(): Call<ApiBerichtSearchResponse> {
        return berichtRepository.getBerichten2()
    }
}
