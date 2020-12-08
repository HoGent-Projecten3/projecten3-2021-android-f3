package com.example.faith.data

import android.os.Message
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.faith.api.ApiService
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import javax.inject.Inject

/**
 * @author Jef Seys
 */
class BerichtRepository @Inject constructor(private val service: ApiService) {
    fun verstuurBericht(mijnEmail: String, andereEmail: String, mijnNaam: String, andereNaam: String, text: String): Call<Message> {

        return service.verstuurBericht(mijnEmail, andereEmail, mijnNaam, andereNaam, text)
    }
    fun getBerichten2(totDatum: String, aantal: Int): Call<ApiBerichtSearchResponse> {
        return service.getBerichten(totDatum, aantal)
    }
}
