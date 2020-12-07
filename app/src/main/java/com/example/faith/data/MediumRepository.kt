package com.example.faith.data

import android.os.Message
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.faith.api.ApiService
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import retrofit2.Call
import javax.inject.Inject

/**
 * @author Remi Mestdagh
 */
class MediumRepository @Inject constructor(
    private val mediumDao: MediumDao,
    private val service: ApiService,
    private val db: AppDatabase
) {


    suspend fun insertOne(medium: Medium) = mediumDao.insertOne(medium)

    fun getMedium(id: Int) = mediumDao.getMedium(id)

    @ExperimentalPagingApi
    fun getSearchResultStream(mediumNaam: String): Flow<PagingData<Medium>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = 20,
                initialLoadSize = 20,
                prefetchDistance = 5
            ),
            remoteMediator = MediumRemoteMediator(db, service, mediumNaam),

            ) {
            mediumDao.getMedia()
        }.flow
    }

    @ExperimentalPagingApi
    fun getDagboekPosts(dagboekNaam: String): Flow<PagingData<Medium>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = 20,
                initialLoadSize = 20,
                prefetchDistance = 20
            ),
            remoteMediator = DagboekRemoteMediator(db, service, dagboekNaam)

        ) {
            mediumDao.getDagboek()
        }.flow
    }

    fun postMedium(imageFile: MultipartBody.Part, beschrijving: String?): Call<Message> {

        return service.uploadMedia(imageFile, beschrijving)
    }

    fun postText(titel: String, beschrijving: String): Call<Message> {
        return service.uploadText(titel, beschrijving)
    }

    fun removeMedium(id: Int): Call<Medium> {
        return service.removeMedium(id)
    }

    suspend fun deleteMediumRoom(id: Int) = mediumDao.deleteMedium(id)
}
