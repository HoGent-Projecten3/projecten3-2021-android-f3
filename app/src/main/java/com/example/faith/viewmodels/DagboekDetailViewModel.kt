package com.example.faith.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.faith.data.MediumRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

/**
 * @author Remi Mestdagh
 */
class DagboekDetailViewModel @AssistedInject constructor(
    private val mediumRepository: MediumRepository,
    @Assisted private val mediumId: Int
) : ViewModel() {

    var dagboek = mediumRepository.getMedium(mediumId)

    @AssistedInject.Factory
    interface AssistedFactory {
        fun create(mediumId: Int): DagboekDetailViewModel
    }

    companion object {
        var instance: DagboekDetailViewModel? = null
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
