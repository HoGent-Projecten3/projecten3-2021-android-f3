package com.example.faith.viewmodels

import android.os.Build
import android.os.Message
import androidx.annotation.RequiresApi
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.faith.data.Medium
import com.example.faith.data.MediumDao
import com.example.faith.data.MediumRepository
import retrofit2.Call
import java.time.LocalDate
import java.util.Date

/**
 * @author Remi Mestdagh
 */
class DagboekViewModel @ViewModelInject constructor(
    private val repository: MediumRepository
) : ViewModel() {
    fun uploadDagboekPost(titel: String, beschrijving: String): Call<Message> {
        return repository.postText(titel, beschrijving)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun insertOne(mediumId:Int, titel: String,beschrijving: String,url: String, mediumType: Int,datum:Date){
        return repository.insertOne(Medium(mediumId,titel,"",beschrijving,4, datum))
    }
}
