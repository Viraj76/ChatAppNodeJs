package com.appsv.chatappcomposenodejs.users.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsv.chatappcomposenodejs.chat.presentation.toMessage
import com.appsv.chatappcomposenodejs.users.data.models.User
import com.appsv.chatappcomposenodejs.users.data.remote.UserService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserViewModel : ViewModel() {

    private val userService: UserService by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.100.105:5000") // Replace with your server URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserService::class.java)
    }

    val users: MutableState<List<User>> = mutableStateOf(emptyList())
    private lateinit var socket: Socket

    init {
        fetchUsers()
        setupSocket()
    }

    private fun setupSocket() {
        try {
            socket = IO.socket("http://192.168.100.105:5000")
            socket.connect()
            socket.on(Socket.EVENT_CONNECT) {
                Log.d("SocketIO", "Connected")
            }
            socket.on("usersUpdated") { args ->
                args?.let { d ->
                    if (d.isNotEmpty()) {
                        val data = d[0].toString()
                        val updateUsers = parseUsers(data)
//                        Log.d("UpdatedUsers", users.toString())
//                        val message = data.getJSONObject("message")
//                        val chat = data.getJSONObject("chatRoomId").toMessage()
//                        Log.d("DATADEBUG","$message")
//                        Log.d("DATADEBUG","$data")
//                        Log.d("DATADEBUG","$chat")
//                        if (data.toString().isNotEmpty()) {
                        val currentUsers = users.value.toMutableList()
//                        currentUsers.add(usersss)
                        users.value = updateUsers
//                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("SocketIO", "Error setting up socket", e)
        }
    }

    private fun fetchUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val fetchedUsers = userService.getUsers()
                for (i in fetchedUsers) {
                    Log.d("users", i.toString())
                }
                users.value = fetchedUsers
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            }
        }
    }
}

fun parseUsers(jsonResponse: String): List<User> {
    val gson = Gson()
    val userListType = object : TypeToken<List<User>>() {}.type
    return gson.fromJson(jsonResponse, userListType)
}
