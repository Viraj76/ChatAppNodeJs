package com.appsv.chatappcomposenodejs.navigation

const val USERS_KEY = "users_key"
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

    data object ChatScreen : Routes(route = "chat_screens")
}