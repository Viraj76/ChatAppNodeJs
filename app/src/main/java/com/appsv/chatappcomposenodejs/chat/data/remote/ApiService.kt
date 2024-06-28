package com.appsv.chatappcomposenodejs.chat.data.remote

import com.appsv.chatappcomposenodejs.chat.data.models.Messages
import com.appsv.chatappcomposenodejs.chat.domain.models.Message
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatRoomService {
    @GET("/messages/{chatRoomId}")
    suspend fun getMessages(@Path("chatRoomId") chatRoomId: String): Messages

    @POST("/messages")
    suspend fun sendMessage(@Body messageData: Message)
}
