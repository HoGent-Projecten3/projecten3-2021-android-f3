package com.example.faith.data

import android.os.Message
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.faith.api.ApiService
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediumRepository @Inject constructor(private val mediumDao: MediumDao, private val service: ApiService) {

    fun getMedia() = mediumDao.getMedia()
    suspend fun insertOne(medium: Medium) = mediumDao.insertOne(medium)

    fun getMedium(id: Int) = mediumDao.getMedium(id)
    fun getSearchResultStream(): Flow<PagingData<ApiPhoto>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 25),
            pagingSourceFactory = { ApiPagingSource(service) }

        ).flow
    }
    fun getMedia2(): Call<ApiMediumSearchResponse> {
        return service.getMedia2(0,500)
    }
    fun getDagboekPosts() : Flow<PagingData<ApiDagboek>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 25),
            pagingSourceFactory = { ApiDagboekPagingSource(service) }

        ).flow

    }
    fun getDagboekPosts2(): Call<ApiDagboekSearchResponse> {
        return service.getDagboek2()
    }
    fun postMedium(imageFile: MultipartBody.Part): Call<Message> {

        return service.uploadMedia(imageFile)

    }
    fun postText(s: String) : Call<Message> {
        return service.uploadText(s)
    }
}
