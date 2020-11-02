package com.example.faith.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.faith.data.ApiDagboek
import com.example.faith.data.ApiDagboekSearchResponse
import com.example.faith.data.Medium
import com.example.faith.data.MediumRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
/**
 * @author Remi Mestdagh
 */
class DagboekListViewModel @ViewModelInject constructor(
    private val repository: MediumRepository
) : ViewModel() {

    fun getDagboekPosts(): Flow<PagingData<ApiDagboek>> {
        return repository.getDagboekPosts()
    }
    fun getDagboekPosts2(): Call<ApiDagboekSearchResponse> {
        return repository.getDagboekPosts2()
    }
    suspend fun saveOne(medium: Medium) {
        repository.insertOne(medium)
    }
}
