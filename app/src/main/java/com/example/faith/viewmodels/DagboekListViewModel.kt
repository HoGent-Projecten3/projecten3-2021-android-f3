package com.example.faith.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.faith.data.ApiDagboek
import com.example.faith.data.MediumRepository
import kotlinx.coroutines.flow.Flow

class DagboekListViewModel @ViewModelInject constructor(
    private val repository: MediumRepository
) : ViewModel() {

    fun getDagboekPosts() : Flow<PagingData<ApiDagboek>>{
        return repository.getDagboekPosts()

    }
}