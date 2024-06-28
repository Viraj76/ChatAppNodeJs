package com.appsv.chatappcomposenodejs.add_users.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.appsv.chatappcomposenodejs.add_users.data.models.User
import com.appsv.chatappcomposenodejs.add_users.data.remote.ApiService
import com.appsv.chatappcomposenodejs.add_users.data.remote.RetrofitClient
import com.appsv.chatappcomposenodejs.navigation.Routes
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun AddUsersScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    val apiService = RetrofitClient.instance.create(ApiService::class.java)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val textState = remember { mutableStateOf("") }

        OutlinedTextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            label = { Text("Enter name") },
            placeholder = { Text("Enter your name here..") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            singleLine = true
        )

        Button(onClick ={
            Log.d("userName" , textState.value)
            scope.launch { saveUser(apiService,User(username = textState.value),navController,textState.value) }
//            navController.navigate(Routes.UsersScreen.passUserName(textState.value))
        } ) {
            Text(text = "Go to users")
        }

        Button(onClick ={
            Log.d("userName" , textState.value)
//            scope.launch { saveUser(apiService,User(username = textState.value),navController,textState.value) }
            navController.navigate(Routes.UsersScreen.passUserName("Viraj"))
        } ) {
            Text(text = "Just Go")
        }

    }
}

fun saveUser(apiService: ApiService, user: User, navController: NavController, value: String) {
    apiService.saveUser(user).enqueue(object : Callback<User> {
        override fun onResponse(call: Call<User>, response: Response<User>) {
            val code = response.code()
            if(code == 201){
                println("User saved successfully")
                navController.navigate(Routes.UsersScreen.passUserName(value))
            }
            else if(code == 409){
                println("User exist already!")
            }
        }

        override fun onFailure(call: Call<User>, t: Throwable) {
            t.printStackTrace()
        }
    })
}