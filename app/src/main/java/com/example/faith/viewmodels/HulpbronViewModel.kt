package com.example.faith.viewmodels

import android.os.Message
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.faith.data.HulpbronRepository
import retrofit2.Call

class HulpbronViewModel @ViewModelInject constructor(
    private val repository: HulpbronRepository
) : ViewModel() {

    fun maakHulpbron(titel: String, beschrijving: String, url: String, telefoonnummer: String, emailadres: String, chatUrl: String): Call<Message> {
        return repository.postHulpbron(titel, beschrijving, url, telefoonnummer, emailadres, chatUrl)
    }
}
