package com.example.faith.data

import androidx.paging.PagingSource
import com.example.faith.api.ApiService

/**
 * @author Jef Seys
 */
private const val API_STARTING_PAGE_INDEX = 0

class ApiBerichtPagingSource(
    private val service: ApiService

) : PagingSource<Int, ApiBericht>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ApiBericht> {
        val page = params.key ?: API_STARTING_PAGE_INDEX
        return try {
            val response = service.getBerichten(page, params.loadSize)
            val berichten = response.results
            LoadResult.Page(
                data = berichten,
                prevKey = if (page == API_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == response.totalPages) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}
