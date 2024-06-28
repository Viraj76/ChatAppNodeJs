package com.appsv.chatappcomposenodejs.add_users.data.remote

import com.appsv.chatappcomposenodejs.add_users.data.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/users")
    fun saveUser(@Body user: User): Call<User>
}