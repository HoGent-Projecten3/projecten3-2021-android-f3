package com.example.faith.viewmodels

import android.os.Message
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.faith.data.ApiTalent
import com.example.faith.data.ApiTalentSearchResponse
import com.example.faith.data.Talent
import com.example.faith.data.TalentRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Call

class TrofeekamerListViewModel @ViewModelInject constructor(
    private val repository: TalentRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

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

    fun getTalenten(): Flow<PagingData<ApiTalent>> {
        return repository.getTalenten(textFilter.value!!, includePublic.value!!, includePrivate.value!!)
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