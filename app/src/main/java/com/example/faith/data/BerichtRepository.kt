package com.example.faith.data

import android.os.Message
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.faith.api.ApiService
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import retrofit2.Call
import java.util.*
import javax.inject.Inject

/**
 * @author Jef Seys
 */
class BerichtRepository @Inject constructor(private val service: ApiService) {
    fun verstuurBericht(mijnEmail:String,andereEmail:String, mijnNaam:String, andereNaam:String,text:String): Call<Message> {

        return service.verstuurBericht(mijnEmail,andereEmail, mijnNaam, andereNaam,text)
    }

    fun getBerichten(): Flow<PagingData<ApiBericht>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 10, initialLoadSize = 10, prefetchDistance = 10),
            pagingSourceFactory = { ApiBerichtPagingSource(service) }

        ).flow
    }
     fun getBerichten2() : Call<ApiBerichtSearchResponse> {
        return service.getBerichten2(0,100)
    }
}