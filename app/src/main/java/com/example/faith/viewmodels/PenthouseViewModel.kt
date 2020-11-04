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

    init {
        instance = this
    }

    private val _doelen = MutableLiveData<List<IDoel>>()
    val doelen: LiveData<List<IDoel>>
        get() = _doelen


    fun getDoelen(){
        repository.getDoelen().enqueue(object : Callback<List<DoelDTO>>{
            override fun onResponse(call: Call<List<DoelDTO>>, response: Response<List<DoelDTO>>) {
                if(response.isSuccessful) {
                    val doelenDTO = response.body()
                    val doelen = mutableListOf<IDoel>()
                    for (doel: DoelDTO in doelenDTO!!) {
                        doelen.add(doel.getDoel())
                    }
                    _doelen.value = doelen
                }else{
                    Log.i("PenthouseViewModel", "Failed to SYNC doelen: ${response.code()}, ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<DoelDTO>>, t: Throwable) {
                Log.i("PenthouseViewModel", "Failed to GET doelen: ${t.toString()}")
            }

        })
    }

    fun postDoelen(){
        val doelenDTO = mutableListOf<DoelDTO>()
        for(doel:IDoel in _doelen.value!!.toList()){
            doelenDTO.add(DoelDTO(doel as Doel))
        }
        for (doel: DoelDTO in doelenDTO){
            System.out.println(doel.toString())
        }
        repository.postDoelen(doelenDTO).enqueue(object: Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.body()!!){
                    Log.i("PenthouseViewModel", "Succesfully posted doelen")
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.i("PenthouseViewModel", "Failed to POST doelen: ${t.toString()}")
            }

        })
    }

    fun syncDoelen(){
        val doelenDTO = mutableListOf<DoelDTO>()
        for(doel:IDoel in _doelen.value!!.toList()){
            doelenDTO.add(DoelDTO(doel as Doel))
        }
        doSyncDoelen(doelenDTO)
    }

    private fun doSyncDoelen(doelenDTO: List<DoelDTO>){
        repository.syncDoelen(doelenDTO).enqueue(object: Callback<List<DoelDTO>>{
            override fun onResponse(call: Call<List<DoelDTO>>, response: Response<List<DoelDTO>>) {
                if(response.isSuccessful) {
                    val doelenDTO = response.body()
                    val doelen = mutableListOf<IDoel>()
                    for (doel: DoelDTO in doelenDTO!!) {
                        doelen.add(doel.getDoel())
                    }
                    _doelen.value = doelen
                }else{
                    Log.i("PenthouseViewModel", "Failed to SYNC doelen: ${response.code()}, ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<DoelDTO>>, t: Throwable) {
                Log.i("PenthouseViewModel", "Failed to SYNC doelen: ${t.toString()}")
            }

        })
    }

    public fun verwijderDoel(teVerwijderenDoel: IDoel){
        val doelen = _doelen.value!!.toMutableList()
        if(!doelen.remove(teVerwijderenDoel)) {
            for (doel: IDoel in doelen) {
                doel.verwijderDoel(teVerwijderenDoel)
            }
        }
        val doelenDTO = mutableListOf<DoelDTO>()
        for(doel:IDoel in doelen){
            doelenDTO.add(DoelDTO(doel as Doel))
        }
        doSyncDoelen(doelenDTO)
    }

    public fun setDoelen(doelen: List<IDoel>){
        _doelen.value = doelen
    }

    public fun addDoel(doel: IDoel){
        val doelen =_doelen.value?.toMutableList()
        doelen?.add(doel)
        _doelen.value = doelen!!
    }

    companion object{
        var instance: PenthouseViewModel? = null
    }
}