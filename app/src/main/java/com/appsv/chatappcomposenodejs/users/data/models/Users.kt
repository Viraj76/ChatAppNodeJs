package com.appsv.chatappcomposenodejs.users.data.models

data class User(
    val _id: String?,
    val username: String?,
    val lastMessage : String ? = "Click here to chat",
    val lastTime : String =""
    // Add more fields as needed
)
