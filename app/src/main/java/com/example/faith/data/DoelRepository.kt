package com.example.faith.data

import com.example.faith.api.ApiService
import com.example.faith.api.MyServiceInterceptor
import okhttp3.ResponseBody
import retrofit2.Call
import javax.inject.Inject

public class DoelRepository @Inject constructor(private val service: ApiService) {

    fun getDoelen(): Call<List<DoelDTO>> {
        return service.getDoelen();
    }
}