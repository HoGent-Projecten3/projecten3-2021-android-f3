package com.example.faith.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.faith.data.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.switchMap
import retrofit2.Call

class HulpbronListViewModel @ViewModelInject constructor(
    private val repository: HulpbronRepository
) : ViewModel() {


    fun getHulpbronnen() : Flow<PagingData<ApiHulpbron>>{
        return repository.getHulpbronnen()
    }
    fun getHulpbronnen2(): Call<ApiHulpbronSearchResponse>{
        return repository.getHulpbronnen2()
    }
    suspend fun saveOne(hulpbron: Hulpbron){
        repository.insertOne(hulpbron)
    }


    fun filter(input: String): Flow<PagingData<ApiHulpbron>>
    {
        val inputLC = input.toLowerCase()
        return repository.getHulpbronnen().map {
            it.filter {
            it.titel.toLowerCase().contains(inputLC)
        }
        }.cachedIn(viewModelScope)
    }
}