package com.example.faith.viewmodels

import android.os.Message
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.faith.data.Medium
import com.example.faith.data.MediumDao
import com.example.faith.data.MediumRepository
import retrofit2.Call
/**
 * @author Remi Mestdagh
 */
class DagboekViewModel @ViewModelInject constructor(
    private val repository: MediumRepository
) : ViewModel() {
    fun uploadDagboekPost(titel: String, beschrijving: String): Call<Message> {
        return repository.postText(titel, beschrijving)
    }
    suspend fun insertOne(mediumId:Int, titel: String,beschrijving: String,url: String, mediumType: Int){
        return repository.insertOne(Medium(mediumId,titel,"",beschrijving,4))
    }
}
