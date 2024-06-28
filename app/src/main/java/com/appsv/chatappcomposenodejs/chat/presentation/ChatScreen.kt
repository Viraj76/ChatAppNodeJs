package com.appsv.chatappcomposenodejs.chat.presentation



import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.appsv.chatappcomposenodejs.chat.components.ChatAppBar
import com.appsv.chatappcomposenodejs.chat.components.MessageBox
import com.appsv.chatappcomposenodejs.chat.components.MessageInputField
import org.bson.types.ObjectId


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    navController: NavController,
    senderId : String,
    receiverId : String,
    viewModel: ChatRoomViewModel
) {
    val topBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topBarState)

    val senderObjectId: ObjectId = ObjectId(senderId)
    val receiverObjectId: ObjectId = ObjectId(receiverId)

    val chatRoomId = "$senderObjectId$receiverObjectId"
    val messages = viewModel.messages

    LaunchedEffect(key1 = Unit) {
        viewModel.getMessages(chatRoomId)
        val mes = messages.value
        for (i in messages.value){
            Log.d("messages" , i.toString())
        }
    }



//    LaunchedEffect(key1 = Unit) {
//        Log.d("iddsss" , "$senderObjectId  $receiverObjectId")
//    }

    Scaffold(
        modifier = Modifier
            .navigationBarsPadding()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets
            .exclude(WindowInsets.navigationBars)
            .exclude(WindowInsets.ime),
        topBar = {
            ChatAppBar(navController = navController)
        },
        containerColor = Color.Transparent,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {





            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                reverseLayout = true,
            ) {
//                items(listOfMessages) { message ->
//                    MessageBox(message = message)
//                }
            }

            MessageInputField(
                navController = navController
            ){
                viewModel.sendMessage(senderId,receiverId,it)
            }



        }
    }
}