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

    fun getMedia() = mediumDao.getMedia()
    suspend fun insertOne(medium: Medium) = mediumDao.insertOne(medium)

    fun getMedium(id: Int) = mediumDao.getMedium(id)
    fun getSearchResultStream(): Flow<PagingData<ApiPhoto>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 10, initialLoadSize = 10, prefetchDistance = 10),
            pagingSourceFactory = { ApiPagingSource(service) }

        ).flow
    }
    fun getMedia2(): Call<ApiMediumSearchResponse> {
        return service.getMedia2(0, 500)
    }
    fun getDagboekPosts(): Flow<PagingData<ApiDagboek>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 50, initialLoadSize = 30, prefetchDistance = 20),
            pagingSourceFactory = { ApiDagboekPagingSource(service) }

        ).flow
    }
    fun getDagboekPosts2(): Call<ApiDagboekSearchResponse> {
        return service.getDagboek2(0, 500)
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
