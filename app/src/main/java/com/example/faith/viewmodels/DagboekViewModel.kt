package com.example.faith.viewmodels

import android.os.Message
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.faith.data.MediumRepository
import retrofit2.Call

class DagboekViewModel @ViewModelInject constructor(
    private val repository: MediumRepository
): ViewModel(){
    fun uploadDagboekPost(titel:String,beschrijving:String) : Call<Message>{
        return repository.postText(titel,beschrijving)
    }

}
