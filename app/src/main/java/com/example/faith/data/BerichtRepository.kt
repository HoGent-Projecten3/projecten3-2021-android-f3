package com.example.faith.data

import android.os.Message
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.faith.api.ApiService
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * @author Jef Seys
 */
class BerichtRepository @Inject constructor(private val service: ApiService, val berichtDao: BerichtDao) {
    fun verstuurBericht(mijnEmail: String, andereEmail: String, mijnNaam: String, andereNaam: String, text: String): Call<Bericht> {

        return service.verstuurBericht(mijnEmail, andereEmail, mijnNaam, andereNaam, text)
    }
    fun getBerichten2(totDatum: String, aantal: Int): Call<ApiBerichtSearchResponse> {
        return service.getBerichten(totDatum, aantal)
    }

    suspend fun deleteBerichten() {
        return berichtDao.deleteAll()
    }
    suspend fun insertOne(bericht:Bericht) {
        return berichtDao.insertOne(bericht)
    }
}
