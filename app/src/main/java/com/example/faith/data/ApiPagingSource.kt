package com.example.faith.data

import androidx.paging.PagingSource
import com.example.faith.api.ApiService

private const val API_STARTING_PAGE_INDEX = 0
/**
 * @author Remi Mestdagh
 */
class ApiPagingSource(
    private val service: ApiService,
    private val mediumRepository: MediumRepository

) : PagingSource<Int, Medium>() {
    override val keyReuseSupported: Boolean
        get() = true
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Medium> {
        val page = params.key ?: API_STARTING_PAGE_INDEX
        return try {
            val response = service.getMedia(page, params.loadSize)

            LoadResult.Page(
                data = response.results,
                prevKey = response.last?.toInt(),
                nextKey = response.next?.toInt()
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}
