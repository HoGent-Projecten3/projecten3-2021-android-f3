package com.example.faith
import android.os.Message
import okhttp3.MultipartBody
import retrofit2.Call;
import retrofit2.http.*


interface APIInterface {

    @Multipart
    @POST
    fun uploadMedia( @Url url:String,
        @Part imageFile: MultipartBody.Part
    ) : Call<Message>
}