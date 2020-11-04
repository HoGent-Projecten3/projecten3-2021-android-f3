package com.example.faith.data

import android.os.Message
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.faith.api.ApiService
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import org.threeten.bp.LocalDateTime
import retrofit2.Call
import java.util.*
import javax.inject.Inject

class BerichtRepository @Inject constructor(private val service: ApiService) {
/*
    fun getBerichten(): Call<List<Bericht>>? {
        val gebruiker1 = Gebruiker("Jef", "Seys", "jef.seys.y0431@student.hogent.be")
        val gebruiker2 = Gebruiker("Joost", "Kaas", "joost@kaas.be")
        val bericht1 = Bericht(1, gebruiker1, gebruiker2, "Hallo", LocalDateTime.now())
        val bericht2 = Bericht(2, gebruiker2, gebruiker1, "Hey", LocalDateTime.now())
        val bericht3 = Bericht(3, gebruiker1, gebruiker2, "Alles goed?", LocalDateTime.now())
        val bericht4 = Bericht(4, gebruiker2, gebruiker1, "Ja hoor! Met jou?", LocalDateTime.now())
        val bericht5 = Bericht(5, gebruiker1, gebruiker2, "Nu zeker Benjamin!", LocalDateTime.now())
        val bericht6 = Bericht(6, gebruiker2, gebruiker1, "Ik vind Android zeer leuk!", LocalDateTime.now())
        val bericht7 = Bericht(7, gebruiker1, gebruiker2, "Kotlin is zo leuk!", LocalDateTime.now())

        return service.getBerichten()//listOf(bericht1, bericht2, bericht3, bericht4, bericht5, bericht6, bericht7)
    }*/

    fun verstuurBericht(bericht: Bericht): Call<Message> {

        return service.verstuurBericht(bericht.verstuurder, bericht.text, bericht.datum)
    }

    fun getBerichten(): Flow<PagingData<ApiBericht>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 10, initialLoadSize = 10, prefetchDistance = 10),
            pagingSourceFactory = { ApiBerichtPagingSource(service) }

        ).flow
    }
}