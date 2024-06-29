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
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class ChatRoomViewModel : ViewModel() {

    private val chatRoomService = ChatRoomRetrofitClient.retrofit.create(ChatRoomService::class.java)

    val messages: MutableState<List<ChatMessages>> = mutableStateOf(emptyList())

//    val liveMessages: StateFlow<List<ChatMessages>> = _messages

    private lateinit var socket: Socket


    init {
        setupSocket()
    }

    private fun setupSocket() {
        try {
            socket = IO.socket("http://192.168.100.105:5000")
            socket.connect()
            socket.on(Socket.EVENT_CONNECT) {
                Log.d("SocketIO", "Connected")
            }
            socket.on("newMessage"){ args->
                args?.let { d ->
                    if (d.isNotEmpty()) {
                        val data = d[0] as JSONObject

                        val message = data.getJSONObject("message").toMessage()
//                        val chat = data.getJSONObject("chatRoomId").toMessage()
                        Log.d("DATADEBUG","$message")
                        Log.d("DATADEBUG","$data")
//                        Log.d("DATADEBUG","$chat")
                        if (data.toString().isNotEmpty()) {
                            val currentMessages = messages.value.toMutableList()
                            currentMessages.add(message)
                            messages.value = currentMessages
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("SocketIO", "Error setting up socket", e)
        }
    }
    private val onNewMessage = Emitter.Listener { args ->
        val data = args[0] as JSONObject
        val chatRoomId = data.getString("chatRoomId")
        val message = data.getJSONObject("message").toMessage()
        Log.d("messages" , message.toString())
        // Update the message list
        val currentMessages = messages.value.toMutableList()
        currentMessages.add(message)
        messages.value = currentMessages
    }

    fun joinRoom(chatRoomId: String) {
        socket.emit("joinRoom", chatRoomId)
    }

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

    override fun onCleared() {
        super.onCleared()
        Log.d("cleared" , "clear io")
        socket.disconnect()
    }

}

fun JSONObject.toMessage(): ChatMessages {
    return ChatMessages(
        _id = getString("_id"),
        sender = getString("sender"),
        message = getString("message"),
        timestamp = getString("timestamp")
    )
}