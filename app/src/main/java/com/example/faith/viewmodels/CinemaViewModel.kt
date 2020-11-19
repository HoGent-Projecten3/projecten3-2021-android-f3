package com.example.faith.viewmodels

import android.os.Message
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.faith.data.MediumRepository
import okhttp3.MultipartBody
import retrofit2.Call
/**
 * @author Remi Mestdagh
 */
class CinemaViewModel @ViewModelInject constructor(
    private val repository: MediumRepository
) : ViewModel() {

    fun uploadMedia(imageFile: MultipartBody.Part, beschrijving: String?): Call<Message> {
        return repository.postMedium(imageFile, beschrijving)
    }
}
