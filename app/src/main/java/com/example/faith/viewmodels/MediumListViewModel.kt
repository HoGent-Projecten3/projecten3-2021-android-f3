package com.example.faith.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.faith.data.ApiPhoto
import com.example.faith.data.ApiMediumSearchResponse
import com.example.faith.data.Medium
import com.example.faith.data.MediumRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Call

class MediumListViewModel @ViewModelInject constructor(
    private val apiRepository: MediumRepository

) : ViewModel() {
    fun searchPictures(): Flow<PagingData<ApiPhoto>> {

        return apiRepository.getSearchResultStream()
    }

    fun getMedia2(): Call<ApiMediumSearchResponse> {
        return apiRepository.getMedia2()
    }
    suspend fun saveOne(medium: Medium) {
        apiRepository.insertOne(medium)
    }
}
