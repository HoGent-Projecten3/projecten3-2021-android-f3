package com.example.faith.viewmodels

import android.os.Message
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.faith.data.ApiMediumResponse
import com.example.faith.data.MediumRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch
import retrofit2.Call

/**
 * @author Remi Mestdagh
 */
class MediumDetailViewModel @AssistedInject constructor(
    private val
    mediumRepository: MediumRepository,
    @Assisted private val mediumId: Int
) : ViewModel() {

    var medium = mediumRepository.getMedium(mediumId)

    fun deleteMediumRoom() {
        viewModelScope.launch {
            if(medium!=null){
                medium.value?.let { mediumRepository.deleteMediumRoom(it) }
            }

        }
    }

    fun removeMedium(): Call<ApiMediumResponse>? {

        return medium.value?.let { mediumRepository.removeMedium(it.mediumId) }
    }

    @AssistedInject.Factory
    interface AssistedFactory {
        fun create(mediumId: Int): MediumDetailViewModel
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
