package com.example.faith.viewmodels

import android.os.Message
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.faith.data.TalentRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch
import retrofit2.Call

/**
 * @author Arne De Schrijver
 */

class TalentDetailViewModel @AssistedInject constructor(
    private val repository: TalentRepository,
    @Assisted private val talentId: Int
) : ViewModel() {

    var talent = repository.getTalentRoom(talentId)

    @AssistedInject.Factory
    interface AssistedFactory {
        fun create(talentId: Int): TalentDetailViewModel
    }

    fun removeTalentApi(): Call<Message> {
        return repository.deleteTalent(talent.value!!.talentId)
    }

    fun deleteTalentRoom() {
        viewModelScope.launch {
                repository.deleteTalentRoom(talent.value!!.talentId)
        }
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            talentId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(talentId) as T
            }
        }
    }
}
