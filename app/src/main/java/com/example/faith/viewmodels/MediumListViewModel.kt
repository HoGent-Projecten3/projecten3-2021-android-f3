package com.example.faith.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.faith.data.ApiPhoto
import com.example.faith.data.Medium
import com.example.faith.data.MediumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MediumListViewModel @ViewModelInject  constructor(
    private val mediumRepository: MediumRepository

) : ViewModel() {
    
    fun searchPictures(): Flow<PagingData<ApiPhoto>> {

        return mediumRepository.getSearchResultStream()
    }


}