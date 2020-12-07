package com.example.faith.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.faith.data.Medium
import com.example.faith.data.MediumRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * @author Remi Mestdagh
 */
class DagboekListViewModel @ViewModelInject constructor(
    private val repository: MediumRepository, @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        const val KEY_START_PAGE = "startkey"
        const val DEFAULT_PAGE = "dagboek"
    }

    init {
        if (!savedStateHandle.contains(KEY_START_PAGE)) {
            savedStateHandle.set(KEY_START_PAGE, DEFAULT_PAGE)
        }
    }

    private val clearListCh = Channel<Unit>(Channel.CONFLATED)



    @ExperimentalPagingApi
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val posts = flowOf(
        clearListCh.receiveAsFlow().map { PagingData.empty<Medium>() },
        savedStateHandle.getLiveData<String>(KEY_START_PAGE)
            .asFlow()
            .flatMapLatest { repository.getDagboekPosts(it) }
            .cachedIn(viewModelScope)
    ).flattenMerge(2)

    @ExperimentalPagingApi
    fun filter(query:String): Flow<PagingData<Medium>> {
        if(query.isNullOrEmpty()){
            return posts
        }
       return posts.map { pagingData ->
            pagingData.filter {
                it.naam.startsWith(query,true)
            }
        }.cachedIn(viewModelScope)
    }

}
