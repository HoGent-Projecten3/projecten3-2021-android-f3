package com.example.faith.data

import com.example.faith.api.ApiService
import com.example.faith.api.MyServiceInterceptor
import com.example.faith.data.DoelDTO
import retrofit2.Call
import javax.inject.Inject

public class DoelRepository @Inject constructor(private val service: ApiService) {

    fun getDoelen(): Call<List<DoelDTO>> {
        return service.getDoelen();
    }

    fun postDoelen(doelenDTO: List<DoelDTO>): Call<Boolean>{
        return service.postDoelen(doelenDTO)
    }

    fun syncDoelen(doelenDTO: List<DoelDTO>): Call<List<DoelDTO>>{
        return service.syncDoelen(doelenDTO)
    }
}