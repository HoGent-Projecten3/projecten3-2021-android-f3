package com.example.faith.viewmodels

import android.os.Message
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
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

    private var _textFilter = MutableLiveData<String>()
    private var _includePublic = MutableLiveData<Boolean>()
    private var _includePrivate = MutableLiveData<Boolean>()

    init {
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
    fun cycleFilter()
    {
        includePublic.value = includePublic.value == false
    }
    fun getHulpbronnen() : Flow<PagingData<ApiHulpbron>>{
        return repository.getHulpbronnen(textFilter.value.toString(), includePublic.value == true, includePrivate.value == true)
    }
    fun getHulpbronnen2(): Call<ApiHulpbronSearchResponse>{
        return repository.getHulpbronnen2(textFilter.value.toString(), includePublic.value == true, includePrivate.value == true)
    }

    fun deleteHulpbron(id:Int): Call<Message> {
        return repository.deleteHulpbron(id)
    }

    suspend fun saveOne(hulpbron: Hulpbron){
        repository.insertOne(hulpbron)
    }

    companion object{
        var instance: HulpbronListViewModel? = null
    }
}