package com.appsv.chatappcomposenodejs.users.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.appsv.chatappcomposenodejs.users.data.models.User
import com.appsv.chatappcomposenodejs.users.domain.models.listOfConversations
import com.appsv.chatappcomposenodejs.users.presentation.components.ConversationItem
import com.appsv.chatappcomposenodejs.users.presentation.components.HomeAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    userViewModel: UserViewModel
) {

    val users: MutableState<List<User>> = userViewModel.users
    Scaffold(
        topBar = {
            HomeAppBar()
        },
        containerColor = Color.Transparent,
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            items(users.value) { conversation ->
                ConversationItem(
                    user = conversation,
                    onClick = {

                    }
                )
            }
        }
    }
}