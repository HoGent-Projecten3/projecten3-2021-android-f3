package com.example.faith.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.faith.data.Medium
import com.example.faith.data.MediumRepository

class MediumListViewModel @ViewModelInject internal constructor(
    mediumRepository: MediumRepository,
    @Assisted private val savedStateHandle: SavedStateHandle

) : ViewModel() {

    val media: LiveData<List<Medium>> =
        mediumRepository.getMedia()


}