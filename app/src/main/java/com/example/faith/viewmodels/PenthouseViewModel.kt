package com.example.faith.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.faith.data.Doel
import com.example.faith.data.DoelDTO
import com.example.faith.data.DoelRepository
import com.example.faith.data.IDoel
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import java.lang.Error

public class PenthouseViewModel @ViewModelInject constructor(val repository: DoelRepository): ViewModel(){

    private val _doelen = MutableLiveData<List<IDoel>>()
    val doelen: LiveData<List<IDoel>>
        get() = _doelen


    fun getGebruikers(){
        repository.getDoelen().enqueue(object : Callback<List<DoelDTO>>{
            override fun onResponse(call: Call<List<DoelDTO>>, response: Response<List<DoelDTO>>) {
                val doelenDTO = response.body()
                val doelen = mutableListOf<IDoel>()
                for (doel: DoelDTO in doelenDTO!!){
                    doelen.add(doel.getDoel())
                }
                _doelen.value = doelen
            }

            override fun onFailure(call: Call<List<DoelDTO>>, t: Throwable) {
                Log.i("PenthouseViewModel", "Failure: ${toString().toString()}")
            }

        })
    }
}