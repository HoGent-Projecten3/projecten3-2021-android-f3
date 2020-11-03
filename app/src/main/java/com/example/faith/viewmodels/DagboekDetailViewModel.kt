package com.example.faith.viewmodels

import android.os.Message
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.faith.data.MediumRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch
import retrofit2.Call

/**
 * @author Remi Mestdagh
 */
class DagboekDetailViewModel @AssistedInject constructor(
    private val mediumRepository: MediumRepository,
    @Assisted private val mediumId: Int
) : ViewModel() {

    var dagboek = mediumRepository.getMedium(mediumId)

    fun deleteMediumRoom(mediumId: Int){
        viewModelScope.launch {
            mediumRepository.deleteMediumRoom(mediumId)
        }
    }
    fun removeMediumApi(): Call<Message> {
        return mediumRepository.removeMedium(dagboek.value!!.mediumId)
    }

    @AssistedInject.Factory
    interface AssistedFactory {
        fun create(mediumId: Int): DagboekDetailViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            mediumId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(mediumId) as T
            }
        }
    }
}
