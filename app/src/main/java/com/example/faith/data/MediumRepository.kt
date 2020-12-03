package com.example.faith.data

import android.os.Message
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
class MediumRepository @Inject constructor(private val mediumDao: MediumDao, private val service: ApiService) {
    suspend fun insertOne(medium: Medium) = mediumDao.insertOne(medium)

    fun getMedium(id: Int) = mediumDao.getMedium(id)
    fun getSearchResultStream(): Flow<PagingData<ApiMediumResponse>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20, initialLoadSize = 20, prefetchDistance = 5),
            pagingSourceFactory = { ApiPagingSource(service, this) }

        ).flow
    }

    fun getDagboekPosts(): Flow<PagingData<ApiDagboek>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 50, initialLoadSize = 30, prefetchDistance = 20),
            pagingSourceFactory = { ApiDagboekPagingSource(service,this) }

        ).flow
    }
    fun postMedium(imageFile: MultipartBody.Part, beschrijving: String?): Call<Message> {

        return service.uploadMedia(imageFile, beschrijving)
    }
    fun postText(titel: String, beschrijving: String): Call<Message> {
        return service.uploadText(titel, beschrijving)
    }
    fun removeMedium(id: Int): Call<ApiMediumResponse> {
        return service.removeMedium(id)
    }
    suspend fun deleteMediumRoom(medium: Medium) = mediumDao.deleteMedium(medium)
}
