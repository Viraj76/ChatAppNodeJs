package com.appsv.chatappcomposenodejs.users.domain.models

data class Conversation(
    val sender: String,
    val amILastSender: Boolean,
    val message: String,
    val time: String,
    val unread: Boolean,
)

val listOfConversations = listOf(
    Conversation(
        sender = "Ann Carroll",
        amILastSender = false,
        message = "Dinner tonight?",
        time = "Mon",
        unread = false,
    )
)