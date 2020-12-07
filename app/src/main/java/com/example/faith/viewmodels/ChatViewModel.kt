package com.example.faith.viewmodels

import android.os.Message
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.faith.data.ApiBerichtSearchResponse
import com.example.faith.data.Bericht
import com.example.faith.data.BerichtRepository
import com.example.faith.data.Gebruiker
import com.example.faith.data.GebruikerRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    fun verstuurBericht(
        mijnEmail: String,
        andereEmail: String,
        mijnNaam: String,
        andereNaam: String,
        text: String
    ): Call<Message> {
        return berichtRepository.verstuurBericht(mijnEmail, andereEmail, mijnNaam, andereNaam, text)
    }

    fun geefBerichten2(totDatum: String, aantal: Int): Call<ApiBerichtSearchResponse> {
        return berichtRepository.getBerichten2(totDatum, aantal)
    }

    fun geefBerichten(totDatum: String, aantal: Int): LiveData<List<Bericht>> {


        berichtRepository.getBerichten2(totDatum,aantal).enqueue(
            object : Callback<ApiBerichtSearchResponse?> {
                override fun onResponse(
                    call: Call<ApiBerichtSearchResponse?>,
                    response: Response<ApiBerichtSearchResponse?>
                ) {
                    viewModelScope.launch {
                        berichtRepository.berichtDao.deleteAll()
                    }

                    response.body()?.berichten?.forEach {

                        viewModelScope.launch {
                            berichtRepository.berichtDao.insertOne(it)
                        }



                    }
                }

                override fun onFailure(call: Call<ApiBerichtSearchResponse?>, t: Throwable) {
                }
            }
        )
        var list: LiveData<List<Bericht>> = berichtRepository.berichtDao.getBerichten()
        return list
    }
}
