package com.example.faith.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.faith.data.Medium
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
