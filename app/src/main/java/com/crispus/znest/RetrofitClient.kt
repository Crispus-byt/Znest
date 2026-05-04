package com.crispus.znest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.crispus.znest.ApiService

object RetrofitClient {

    private const val BASE_URL = "http://192.168.1.104:5000/chat"

    val instance: ApiService by lazy {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}