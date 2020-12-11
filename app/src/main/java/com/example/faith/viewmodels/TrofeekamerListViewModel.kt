package com.example.faith.viewmodels

import android.os.Message
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
import com.example.faith.data.Talent
import com.example.faith.data.TalentRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import retrofit2.Call

class TrofeekamerListViewModel @ViewModelInject constructor(
    private val repository: TalentRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _textFilter = MutableLiveData<String>()
    private var _includePublic = MutableLiveData<Boolean>()
    private var _includePrivate = MutableLiveData<Boolean>()
    private val clearListCh = Channel<Unit>(Channel.CONFLATED)
    init {
        if (!savedStateHandle.contains(KEY_START_PAGE)) {
            savedStateHandle.set(KEY_START_PAGE, DEFAULT_PAGE)
        }
        textFilter.value = ""
        _includePublic.value = true
        _includePrivate.value = true
        instance = this;
    }
    @ExperimentalPagingApi
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val posts = flowOf(
        clearListCh.receiveAsFlow().map { PagingData.empty<Talent>() },
        savedStateHandle.getLiveData<String>(HulpbronListViewModel.KEY_START_PAGE)
            .asFlow()
            .flatMapLatest {
                repository.getTalentenPaging(
                     it
                )
            }
            .cachedIn(viewModelScope)
    ).flattenMerge(2)


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

    fun getTalenten(): Flow<PagingData<Talent>> {
        return repository.getTalenten()
    }
    fun getGedeeldeTalenten(): Flow<PagingData<Talent>> {
        return repository.getGedeeldeTalenten()
    }

    fun deleteTalent(id: Int): Call<Message> {
        return repository.deleteTalent(id)
    }

    suspend fun saveOne(talent: Talent) {
        repository.insertOne(talent)
    }

    companion object {
        var instance: TrofeekamerListViewModel? = null
        const val KEY_START_PAGE = "startkey"
        const val DEFAULT_PAGE = "0"
    }

}