package com.example.faith.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.faith.data.ApiMediumResponse
import com.example.faith.data.ApiMediumSearchResponse
import com.example.faith.data.Medium
import com.example.faith.data.MediumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import retrofit2.Call
/**
 * @author Remi Mestdagh
 */
class MediumListViewModel @ViewModelInject constructor(
    private val apiRepository: MediumRepository

) : ViewModel() {
    fun searchPictures(): Flow<PagingData<ApiMediumResponse>> {

        return apiRepository.getSearchResultStream().cachedIn(viewModelScope)
    }

    fun filter(naam: String): Flow<PagingData<ApiMediumResponse>> {
        return apiRepository.getSearchResultStream().map {
            it.filter {
                it.naam.startsWith(naam)
            }
        }.cachedIn(viewModelScope)
    }
}
