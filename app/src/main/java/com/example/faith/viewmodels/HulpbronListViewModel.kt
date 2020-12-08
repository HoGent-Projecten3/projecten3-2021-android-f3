package com.example.faith.viewmodels

import android.os.Message
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.faith.data.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.switchMap
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
        instance = this;
    }

    var textFilter: MutableLiveData<String>
        get() = _textFilter
        set(value) {
            _textFilter = value;
        }

    var includePublic: MutableLiveData<Boolean>
        get() = _includePublic
        set(value) {
            _includePublic = value;
        }

    var includePrivate: MutableLiveData<Boolean>
        get() = _includePrivate
        set(value) {
            _includePrivate = value;
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
                    textFilter.value!!, includePublic.value!!,
                    includePrivate.value!!, it
                )
            }
            .cachedIn(viewModelScope)
    ).flattenMerge(2)

    fun deleteHulpbron(id: Int): Call<Boolean> {
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