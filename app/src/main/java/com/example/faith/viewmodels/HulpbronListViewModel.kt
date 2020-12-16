package com.example.faith.viewmodels

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.faith.data.Hulpbron
import com.example.faith.data.HulpbronRepository
import com.example.faith.data.Medium
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Call

class HulpbronListViewModel @ViewModelInject constructor(
    private val repository: HulpbronRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val clearListCh = Channel<Unit>(Channel.CONFLATED)

    private var _textFilter = MutableLiveData<String>()
    private var _includePublic = MutableLiveData<Boolean>()
    private var _includePrivate = MutableLiveData<Boolean>()

    init {
        if (!savedStateHandle.contains(KEY_START_PAGE)) {
            savedStateHandle.set(KEY_START_PAGE, DEFAULT_PAGE)
        }
        textFilter.value = ""
        _includePublic.value = true
        _includePrivate.value = true
        instance = this
    }

    var textFilter: MutableLiveData<String>
        get() = _textFilter
        set(value) {
            _textFilter = value
        }

    var includePublic: MutableLiveData<Boolean>
        get() = _includePublic
        set(value) {
            _includePublic = value
        }

    var includePrivate: MutableLiveData<Boolean>
        get() = _includePrivate
        set(value) {
            _includePrivate = value
        }

    fun cycleFilter() {
        includePublic.value = includePublic.value == false
    }

    @ExperimentalPagingApi
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val posts = flowOf(
        clearListCh.receiveAsFlow().map { PagingData.empty<Hulpbron>() },
        savedStateHandle.getLiveData<String>(KEY_START_PAGE)
            .asFlow()
            .flatMapLatest {
                repository.getHulpbronnen(
                    textFilter.value!!,
                    includePublic.value!!,
                    includePrivate.value!!,
                    it
                )
            }
            .cachedIn(viewModelScope)
    ).flattenMerge(2)

    @ExperimentalPagingApi
    fun filter(): Flow<PagingData<Hulpbron>> {
        return posts.map { pagingData ->
            pagingData.filter {
                if (textFilter.value.isNullOrEmpty()) {
                    if (includePublic.value == true)
                    {
                        true
                    } else {
                       it.auteurType.equals("Client")
                    }
                } else {
                    if (includePublic.value == true)
                    {
                        it.titel.startsWith(textFilter.value!!, true)
                    } else {
                        it.titel.startsWith(textFilter.value!!, true).and(it.auteurType == "Client")
                    }
                }
            }
        }.cachedIn(viewModelScope)
    }

    fun deleteHulpbron(id: Int): Call<Int> {
        return repository.deleteHulpbron(id)
    }

    fun deleteHulpbronRoom(hulpbronId: Int) {
        viewModelScope.launch {
            repository.deleteHulpbronRoom(hulpbronId)
        }
    }

    companion object {
        var instance: HulpbronListViewModel? = null
        const val KEY_START_PAGE = "startkey"
        const val DEFAULT_PAGE = "hulpbron"
    }
}
