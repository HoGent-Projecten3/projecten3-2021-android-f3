package com.example.faith.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.faith.api.ApiService
import retrofit2.Call
import kotlinx.coroutines.flow.Flow
import android.os.Message
import androidx.paging.ExperimentalPagingApi
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
    ): Flow<PagingData<Talent>> {
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

    fun getGedeeldeTalenten(): Flow<PagingData<Talent>> {
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


    @ExperimentalPagingApi
    fun getTalentenPaging(talentNaam:String): Flow<PagingData<Talent>> {
        return Pager(config = PagingConfig(
            enablePlaceholders = false,
            pageSize = 20,
            initialLoadSize = 20,
            prefetchDistance = 20
        ),
        remoteMediator = TalentRemoteMediator(db, service,talentNaam)
            ) {
            talentDao.getAll()
        }
            .flow
    }

    fun getTalent(id: Int) = service.getItem(id)
    fun getTalentRoom(id: Int) = talentDao.getTalent(id)


    fun postTalent(inhoud:String): Call<Message> {
        return service.postTalent(inhoud);
    }

    fun deleteTalent(id: Int):Call<Message>{
        return service.removeTalent(id)
    }

    suspend fun deleteTalentRoom(talentId: Int) = talentDao.deleteTalent(talentId)

    suspend fun insertOne(talent: Talent) = talentDao.insertOne(talent)

}