package com.example.faith.viewmodels

import android.os.Message
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.faith.data.MediumRepository
import okhttp3.MultipartBody
import retrofit2.Call

class CinemaViewModel @ViewModelInject constructor(
    private val repository: MediumRepository
) :ViewModel() {

    fun uploadMedia(imageFile: MultipartBody.Part): Call<Message> {
        return repository.postMedium(imageFile)
    }
    fun uploadText(s:String) : Call<Message>{
        return repository.postText(s)
    }
}