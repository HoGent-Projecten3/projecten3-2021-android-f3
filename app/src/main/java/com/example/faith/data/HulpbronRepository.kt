package com.example.faith.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.map
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.faith.api.ApiService
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Call

/**
 * Repository module for handling data operations.
 */
@Singleton
class HulpbronRepository @Inject constructor(private val hulpbronDao: HulpbronDao, private val service: ApiService, private val database : AppDatabase) {

    fun getHulpbronnen() : Flow<PagingData<ApiHulpbron>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 10, initialLoadSize = 10,prefetchDistance = 10),
            pagingSourceFactory = { ApiHulpbronPagingSource(service) }

        ).flow
    }

    fun getHulpbronnen2(): Call<ApiHulpbronSearchResponse> {
        return service.getHulpbronnen2(0,500)
    }
    fun getHulpbron(id: Int) = hulpbronDao.getOne(id)



    suspend fun insertOne(hulpbron: Hulpbron) = hulpbronDao.insertOne(hulpbron)

}