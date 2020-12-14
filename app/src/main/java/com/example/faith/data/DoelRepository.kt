package com.example.faith.data

import com.example.faith.api.ApiService
import retrofit2.Call
import javax.inject.Inject

// @Singleton
class DoelRepository @Inject constructor(/*private val doelDao: DoelDao,*/private val service: ApiService) {

    fun getDoelen(): Call<List<Doel>> {
        return service.getDoelen()
    }

    fun postDoelen(doelen: List<Doel>): Call<Boolean> {
        return service.postDoelen(doelen)
    }

    fun syncDoelen(doelen: List<Doel>): Call<List<Doel>> {
        return service.syncDoelen(doelen)
    }

    /*suspend fun insertDoelen(doelen: List<Doel>){
        doelDao.insertAll(doelen)
    }

    fun getDoelenFromRoom(): LiveData<List<Doel>> {
        return doelDao.getAll()
    }*/
}
