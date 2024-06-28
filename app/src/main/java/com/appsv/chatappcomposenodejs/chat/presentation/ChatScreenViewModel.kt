package com.appsv.chatappcomposenodejs.chat.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsv.chatappcomposenodejs.chat.data.models.ChatMessages
import com.appsv.chatappcomposenodejs.chat.data.remote.ChatRoomRetrofitClient
import com.appsv.chatappcomposenodejs.chat.data.remote.ChatRoomService
import com.appsv.chatappcomposenodejs.chat.domain.models.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatRoomViewModel : ViewModel() {

    private val chatRoomService = ChatRoomRetrofitClient.retrofit.create(ChatRoomService::class.java)

    val messages: MutableState<List<ChatMessages>> = mutableStateOf(emptyList())

    fun getMessages(chatRoomId: String) {
        viewModelScope.launch {
            try {
                val fetchedMessage = chatRoomService.getMessages(chatRoomId)
                messages.value = fetchedMessage.messages
            } catch (e: Exception) {
                // Handle error
                Log.d("messages" , e.toString())
                e.printStackTrace()
            }
        }
    }

    fun sendMessage(senderId: String, receiverId: String, message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = chatRoomService.sendMessage(
                    Message(senderId, receiverId, message)
                )
                // Handle success response
            } catch (e: Exception) {
                // Handle error
            }
        }
    }



}
