package com.example.faith.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.faith.data.ApiPhoto
import com.example.faith.data.MediumApiRepository
import kotlinx.coroutines.flow.Flow

class MediumListViewModel @ViewModelInject  constructor(
    private val apiRepository: MediumApiRepository

) : ViewModel() {
    suspend fun searchPictures(): Flow<PagingData<ApiPhoto>> {

        return apiRepository.getSearchResultStream()
    }


}