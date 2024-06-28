package com.appsv.chatappcomposenodejs.users.data.remote

import com.appsv.chatappcomposenodejs.users.data.models.User
import retrofit2.http.GET

interface UserService {
    @GET("/users")
   suspend fun getUsers(): List<User>
}
