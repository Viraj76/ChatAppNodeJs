package com.appsv.chatappcomposenodejs.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.appsv.chatappcomposenodejs.add_users.presentation.AddUsersScreen
import com.appsv.chatappcomposenodejs.users.presentation.UserViewModel
import com.appsv.chatappcomposenodejs.users.presentation.UsersScreen

@Composable
fun SetUpNavGraph(
    modifier: Modifier = Modifier,
) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.AddUsersScreen.route ){
        
        composable(
            route = Routes.AddUsersScreen.route
        ){
            AddUsersScreen(navController = navController)
        }

        composable(
            route = Routes.UsersScreen.route,
            arguments = listOf(
                navArgument(USERS_KEY){type = NavType.StringType}
            )
        ){
            val userName = it.arguments?.getString(USERS_KEY)
            LaunchedEffect(key1 = Unit) {
                Log.d("userName" , userName.toString())
            }
            val viewModel =  viewModel<UserViewModel>()
            UsersScreen(navController = navController,  userViewModel = viewModel)
        }

    }

}