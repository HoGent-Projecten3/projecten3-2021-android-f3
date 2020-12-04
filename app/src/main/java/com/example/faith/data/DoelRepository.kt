package com.example.faith.data

import com.example.faith.api.ApiService
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
class DoelRepository @Inject constructor(/*private val doelDao: DoelDao, */private val service: ApiService) {

    fun getDoelen(): Call<List<DoelDTO>> {
        return service.getDoelen()
    }

    fun postDoelen(doelenDTO: List<DoelDTO>): Call<Boolean> {
        return service.postDoelen(doelenDTO)
    }

    fun syncDoelen(doelenDTO: List<DoelDTO>): Call<List<DoelDTO>> {
        return service.syncDoelen(doelenDTO)
    }

    /*suspend fun insertDoelen(doelenDTO: List<DoelDTO>){
        doelDao.insertAll(doelenDTO)
    }*/
}
