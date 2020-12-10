package com.example.faith.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.faith.api.ApiService
import retrofit2.Call
import kotlinx.coroutines.flow.Flow
import android.os.Message
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Arne De Schrijver
 */

@Singleton
class TalentRepository @Inject constructor(
    private val talentDao: TalentDao,
    private val service: ApiService,
    private val db: AppDatabase
) {

    fun getTalenten(
    ): Flow<PagingData<ApiTalent>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = 20,
                initialLoadSize = 20,
                prefetchDistance = 20
            ),
            pagingSourceFactory = { ApiTalentPagingSource(service,0) }
        ).flow
    }

    fun getGedeeldeTalenten(): Flow<PagingData<ApiTalent>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = 20,
                initialLoadSize = 20,
                prefetchDistance = 20
            ),
        pagingSourceFactory = { ApiTalentPagingSource(service,1) }
        ).flow
    }

    fun getTalent(id: Int) = talentDao.getTalent(id)

    fun postTalent(inhoud:String): Call<Message> {
        return service.postTalent(inhoud);
    }

    fun deleteTalent(id: Int):Call<Talent>{
        return service.removeTalent(id)
    }

    suspend fun deleteTalentRoom(talent: Talent) = talentDao.deleteTalent(talent)

    suspend fun insertOne(talent: Talent) = talentDao.insertOne(talent)

}