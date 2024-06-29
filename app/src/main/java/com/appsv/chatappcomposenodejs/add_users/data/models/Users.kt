package com.appsv.chatappcomposenodejs.add_users.data.models

data class User(
    val username: String,
    val lastMessage : String ? = "Click here to chat",
    val lastTime : String =""

)