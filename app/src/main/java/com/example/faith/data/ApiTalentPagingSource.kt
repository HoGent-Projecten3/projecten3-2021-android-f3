package com.example.faith.data

import androidx.paging.PagingSource
import com.example.faith.api.ApiService

/**
 * @author Arne De Schrijver
 */

private const val API_STARTING_PAGE_INDEX = 0

class ApiTalentPagingSource(
    private val service: ApiService,
    private val soort: Int

) : PagingSource<Int, Talent>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Talent> {
        val page = params.key ?: API_STARTING_PAGE_INDEX
        return try {
            var response: ApiTalentSearchResponse
            if (soort == 0) {
                response = service.getTalenten(page, params.loadSize)
            } else {
                response = service.getGedeeldeTalenten(page, params.loadSize)
            }

            val talenten = response.resultaten
            LoadResult.Page(
                data = talenten,
                prevKey = if (page == API_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == response.totalPages) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}
