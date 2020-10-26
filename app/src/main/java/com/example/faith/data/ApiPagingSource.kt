package com.example.faith.data

import androidx.paging.PagingSource
import com.example.faith.api.ApiService

private const val API_STARTING_PAGE_INDEX =1

class ApiPagingSource(private val service: ApiService

) : PagingSource<Int, ApiPhoto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ApiPhoto> {
        val page = params.key ?: API_STARTING_PAGE_INDEX
        return try {
            val response = service.getMedia()
            val photos = response.results

            LoadResult.Page(
                data = photos,
                prevKey = if (page == API_STARTING_PAGE_INDEX) null else page - 1,
                nextKey =  page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }


}