package com.example.faith.data

import androidx.paging.PagingSource
import com.example.faith.api.ApiService

private const val API_STARTING_PAGE_INDEX = 0
/**
 * @author Remi Mestdagh
 */
class ApiDagboekPagingSource(
    private val service: ApiService,
    private val mediumRepository: MediumRepository

) : PagingSource<Int, ApiDagboek>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ApiDagboek> {
        val page = params.key ?: API_STARTING_PAGE_INDEX
        return try {
            val response = service.getDagboek(page, params.loadSize)
            val photos = response.results
            photos.forEach {
                mediumRepository.insertOne(
                    Medium(
                        it.mediumId,
                        it.naam,"",
                        it.beschrijving,4
                    )
                )
            }
            LoadResult.Page(
                data = photos,
                prevKey = if (page == API_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == response.totalPages) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}
