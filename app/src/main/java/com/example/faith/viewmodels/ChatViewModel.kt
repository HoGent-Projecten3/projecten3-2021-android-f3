package com.example.faith.viewmodels
import android.os.Message
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.faith.data.ApiBerichtSearchResponse
import com.example.faith.data.BerichtRepository
import com.example.faith.data.Gebruiker
import com.example.faith.data.GebruikerRepository
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

    fun geefBerichten2(totDatum: String, aantal: Int): Call<ApiBerichtSearchResponse> {
        return berichtRepository.getBerichten2(totDatum, aantal)
    }
}
