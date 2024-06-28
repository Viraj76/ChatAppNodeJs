package com.appsv.chatappcomposenodejs.navigation

const val USERS_KEY = "users_key"
const val SENDER_KEY = "SENDER_KEY"
const val RECEIVER_KEY = "RECEIVER_KEY"

sealed class Routes(
    val route : String
){
    data object AddUsersScreen : Routes(route = "add_users_screens")

    data object UsersScreen : Routes(route = "users_screens/{$USERS_KEY}") {
        fun passUserName(userName : String) : String{
            return "users_screens/{$USERS_KEY}"
                .replace("{$USERS_KEY}" , userName)
        }
    }

    data object ChatScreen : Routes(route = "chat_screens/{$SENDER_KEY}/{$RECEIVER_KEY}") {
        fun passKeys(senderId : String, receiverId : String) : String{
            return "chat_screens/{$SENDER_KEY}/{$RECEIVER_KEY}"
                .replace(
                    oldValue = "{$SENDER_KEY}",
                    newValue = senderId
                )
                .replace(
                    "{$RECEIVER_KEY}",
                    receiverId
                )
        }
    }
}