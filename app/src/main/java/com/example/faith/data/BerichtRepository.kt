package com.example.faith.data

import android.os.Message
import com.example.faith.api.ApiService
import okhttp3.MultipartBody
import retrofit2.Call
import java.util.*
import javax.inject.Inject

class BerichtRepository @Inject constructor(private val service: ApiService) {

    fun getBerichten():List<Bericht>{
        val gebruiker1 = Gebruiker("Jef", "Seys", "jef.seys.y0431@student.hogent.be")
        val gebruiker2 = Gebruiker("Joost", "Kaas", "joost@kaas.be")
        val bericht1 = Bericht(gebruiker1, "Hallo", Date())
        val bericht2 = Bericht(gebruiker2, "Hey", Date())
        val bericht3 = Bericht(gebruiker1, "Alles goed?", Date())
        val bericht4 = Bericht(gebruiker2, "Ja hoor! Met jou?", Date())
        val bericht5 = Bericht(gebruiker1, "Nu zeker Benjamin!", Date())
        val bericht6 = Bericht(gebruiker2, "Ik vind Android zeer leuk!", Date())
        val bericht7 = Bericht(gebruiker1, "Kotlin is zo leuk!", Date())
        return listOf(bericht1, bericht2, bericht3, bericht4, bericht5, bericht6, bericht7)
    }

    fun verstuurBericht(bericht: Bericht): Call<Message> {

        return service.verstuurBericht(bericht.verstuurder, bericht.text, bericht.datum)
    }
}