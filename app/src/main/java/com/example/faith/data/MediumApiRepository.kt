package com.example.faith.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.faith.api.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MediumApiRepository @Inject constructor(private val service: ApiService) {

    fun getSearchResultStream(): Flow<PagingData<ApiPhoto>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false,pageSize = 25),
            pagingSourceFactory = {ApiPagingSource(service)}

        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 25
    }
}