package com.appsv.chatappcomposenodejs

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.appsv.chatappcomposenodejs.chat.data.models.ChatMessages
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

class SocketHandler {
    private var socket : Socket? = null

    private var _onNewChat = MediatorLiveData<ChatMessages>()
    val onNewChat : LiveData<ChatMessages> get() = _onNewChat

    companion object{
        private const val SOCKET_URL = "http://192.168.100.105:5000/"
        const val  NEW_MESSAGES = "new_message"
        const val BROADCAST = "broadcast"
    }

    init {
        try {
            socket = IO.socket(SOCKET_URL)
            socket?.connect()
            registerOnNewChat()
        }
        catch (e : URISyntaxException){
            e.printStackTrace()
        }
    }

    private fun registerOnNewChat() {
        socket?.on(BROADCAST) { args->
            args?.let { d ->
                if (d.isNotEmpty()) {
                    val data = d[0]              // was giving one error
                    Log.d("DATADEBUG","$data")
                    if (data.toString().isNotEmpty()) {
                        val chat = Gson().fromJson(data.toString(), ChatMessages::class.java)
                        _onNewChat.postValue(chat)
                    }
                }
            }
        }
    }


    fun disconnectSocket(){
        socket?.apply {
            disconnect()
            off()
        }
    }



    fun emitChat(chat : ChatMessages){
        val json = Gson().toJson(chat , ChatMessages::class.java)
        socket?.emit(NEW_MESSAGES , json)
    }

}