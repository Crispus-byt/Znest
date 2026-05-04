package com.crispus.znest

import com.crispus.znest.ChatRequest
import com.crispus.znest.ChatResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("chat")
    fun sendMessage(@Body request: ChatRequest): Call<ChatResponse>
}