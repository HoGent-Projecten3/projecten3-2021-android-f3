package com.example.faith.data

import androidx.paging.PagingSource
import com.example.faith.api.ApiService

private const val API_STARTING_PAGE_INDEX = 1

class ApiDagboekPagingSource(
    private val service: ApiService

) : PagingSource<Int, ApiDagboek>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ApiDagboek> {
        val page = params.key ?: API_STARTING_PAGE_INDEX
        return try {
            val response = service.getDagboek()
            val photos = response.results
            LoadResult.Page(
                data = photos,
                prevKey = if (page == API_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}