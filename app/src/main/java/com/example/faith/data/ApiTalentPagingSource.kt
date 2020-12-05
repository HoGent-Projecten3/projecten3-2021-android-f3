package com.example.faith.data

import androidx.paging.PagingSource
import com.example.faith.api.ApiService

/**
 * @author Arne De Schrijver
 */

private const val API_STARTING_PAGE_INDEX = 0

class ApiTalentPagingSource(
    private val service: ApiService

) : PagingSource<Int, ApiTalent>()  {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ApiTalent> {
        val page = params.key ?: API_STARTING_PAGE_INDEX
        return try {
            val response = service.getTalenten(page,params.loadSize)
            val talenten = response.resultaten
            LoadResult.Page(
                data = talenten,
                prevKey = if (page == API_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == response.aantal_paginas) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}