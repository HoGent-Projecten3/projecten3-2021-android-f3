package com.example.faith.data

import androidx.paging.PagingSource
import com.example.faith.api.ApiService

private const val API_STARTING_PAGE_INDEX = 0

class ApiHulpbronPagingSource(
        private val service: ApiService,
        private val textFilter: String,
        private val includePublic: Boolean,
        private val includePrivate: Boolean


) : PagingSource<Int, Hulpbron>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Hulpbron> {
        val page = params.key ?: API_STARTING_PAGE_INDEX
        return try {
            val response = service.getHulpbronnen(textFilter, includePublic, includePrivate, page = page, perPage = params.loadSize)
            val hulpbronnen = response.results
            LoadResult.Page(
                    data = hulpbronnen,
                    prevKey = if (page == API_STARTING_PAGE_INDEX) null else page - 1,
                    nextKey =  if (page == response.totalPages) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}