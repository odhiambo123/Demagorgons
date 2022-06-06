package com.example.memeoroid.retrofit

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET

interface RetroApiInterface {

    //singleton
    @GET("memes2.json")
    suspend fun getAllTemplates() : Response<List<MemeTemplate>>

    companion object {
        var BASE_URL = "https://jshev.github.io/"
        fun create(): RetroApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(RetroApiInterface::class.java)
        }
    }

}