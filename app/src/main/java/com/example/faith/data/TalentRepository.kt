package com.example.faith.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.faith.api.ApiService
import retrofit2.Call
import kotlinx.coroutines.flow.Flow
import android.os.Message
import javax.inject.Inject

/**
 * @author Arne De Schrijver
 */

public class TalentRepository @Inject constructor(private val talentDao: TalentDao, private val service: ApiService){

    fun getTalent(id: Int) = talentDao.getTalent(id)

    fun postTalent(inhoud:String): Call<Message> {
        return service.postTalent(inhoud);
    }

    fun getTalenten(): Flow<PagingData<ApiTalent>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 10, initialLoadSize = 10, prefetchDistance = 10),
            pagingSourceFactory = { ApiTalentPagingSource(service) }
        ).flow
    }

    fun getTalenten2(): Call<ApiTalentSearchResponse> {
        return service.getTalenten2(0, 100);
    }

    fun removeTalent(id: Int):Call<Message>{
        return service.removeTalent(id)
    }

    suspend fun deleteTalentRoom(talentId:Int)=talentDao.deleteTalent(talentId)

}