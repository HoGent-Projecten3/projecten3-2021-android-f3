package com.example.faith.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.faith.data.HulpbronRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class HulpbronDetailViewModel @AssistedInject constructor(
    hulpbronRepository: HulpbronRepository,
    @Assisted private val hulpbronId: Int
) : ViewModel() {

    var hulpbron = hulpbronRepository.getHulpbron(hulpbronId)

    @AssistedInject.Factory
    interface AssistedFactory {
        fun create(hulpbronId: Int): HulpbronDetailViewModel
    }
    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            hulpbronId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(hulpbronId) as T
            }
        }
    }
}
