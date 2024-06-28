package com.appsv.chatappcomposenodejs.chat.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ChatRoomRetrofitClient {
    private const val BASE_URL = "http://192.168.100.105:5000"
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
