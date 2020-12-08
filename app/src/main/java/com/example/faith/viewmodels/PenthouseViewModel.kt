package com.example.faith.viewmodels

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.faith.data.Doel
import com.example.faith.data.DoelRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PenthouseViewModel @ViewModelInject constructor(val repository: DoelRepository) :
    ViewModel() {

    init {
        instance = this
    }

    private var _doelen = MutableLiveData<List<Doel>>()
    val doelen: LiveData<List<Doel>>
        get() = _doelen

    fun getDoelen() {
        /*viewModelScope.launch {
            //_doelen.value = repository.getDoelenFromRoom().value
            Log.i("PenthouseViewModel", repository.getDoelenFromRoom().value.toString())
        }*/
        repository.getDoelen().enqueue(
            object : Callback<List<Doel>> {
                override fun onResponse(call: Call<List<Doel>>, response: Response<List<Doel>>) {
                    if (response.isSuccessful) {
                        /*viewModelScope.launch {
                            repository.insertDoelen(response.body()!!)
                            Log.i("PenthouseViewModel", response.body()!!.toString())
                        }*/
                        _doelen.value = response.body()
                    } else {
                        Log.i(
                            "PenthouseViewModel",
                            "Failed to SYNC doelen: ${response.code()}, ${response.message()}"
                        )
                    }
                }

                override fun onFailure(call: Call<List<Doel>>, t: Throwable) {
                    Log.i("PenthouseViewModel", "Failed to GET doelen: $t")
                }
            }
        )
    }

    fun postDoelen() {
        repository.postDoelen(_doelen.value!!).enqueue(
            object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if (response.isSuccessful) {
                        Log.i("PenthouseViewModel", "Succesfully posted doelen")
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.i("PenthouseViewModel", "Failed to POST doelen: $t")
                }
            }
        )
    }

    fun syncDoelen() {
        doSyncDoelen(_doelen.value!!)
    }

    private fun doSyncDoelen(doelen: List<Doel>) {
        repository.syncDoelen(doelen).enqueue(
            object : Callback<List<Doel>> {
                override fun onResponse(call: Call<List<Doel>>, response: Response<List<Doel>>) {
                    if (response.isSuccessful) {
                        _doelen.value = response.body()
                    } else {
                        Log.i(
                            "PenthouseViewModel",
                            "Failed to SYNC doelen: ${response.code()}, ${response.message()}"
                        )
                    }
                }

                override fun onFailure(call: Call<List<Doel>>, t: Throwable) {
                    Log.i("PenthouseViewModel", "Failed to SYNC doelen: $t")
                }
            }
        )
    }

    fun getDoel(inhoud: String): Doel? {
        for (doel: Doel in _doelen.value!!) {
            val target = doel.getDoel(inhoud)
            if (target != null) return target
        }
        return null
    }

    fun verwijderDoel(teVerwijderenDoel: Doel) {
        val doelen = _doelen.value!!.toMutableList()
        if (!doelen.remove(teVerwijderenDoel)) {
            for (doel: Doel in doelen) {
                doel.verwijderDoel(teVerwijderenDoel)
            }
        }
        doSyncDoelen(doelen)
    }

    fun addDoel(doel: Doel) {
        val doelen = _doelen.value?.toMutableList()
        doelen?.add(doel)
        _doelen.value = doelen!!
    }

    companion object {
        var instance: PenthouseViewModel? = null
    }
}
