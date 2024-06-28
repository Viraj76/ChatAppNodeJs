package com.appsv.chatappcomposenodejs.users.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsv.chatappcomposenodejs.users.data.models.User
import com.appsv.chatappcomposenodejs.users.data.remote.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val fetchedUsers = userService.getUsers()
                for(i in fetchedUsers){
                    Log.d("users" , i.toString())
                }
                users.value = fetchedUsers
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            }
        }
    }
}
