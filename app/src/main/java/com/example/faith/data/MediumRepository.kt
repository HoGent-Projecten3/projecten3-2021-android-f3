package com.example.faith.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.faith.api.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediumRepository @Inject constructor(private val mediumDao:MediumDao,private val service: ApiService) {

    fun getMedia()= mediumDao.getMedia()
    fun insertOne(medium:Medium) = mediumDao.insertOne(medium)

    fun getMedium(id:Int) = mediumDao.getMedium(id)
    fun getSearchResultStream(): Flow<PagingData<ApiPhoto>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false,pageSize = 25),
            pagingSourceFactory = {ApiPagingSource(service)}

        ).flow
    }
}