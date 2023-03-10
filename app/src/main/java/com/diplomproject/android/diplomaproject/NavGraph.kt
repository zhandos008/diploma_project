package com.diplomproject.android.diplomaproject

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.diplomproject.android.diplomaproject.Screen.SignInScreen
import com.diplomproject.android.diplomaproject.Screen.SignUpScreen
import com.diplomproject.android.diplomaproject.Screen.StartAppScreen

@Composable
fun Setup(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.StartApp.route
        ) {
        composable(
            route = Screens.StartApp.route
        ) {
            StartAppScreen(navController)
        }
        composable(
            route = Screens.SignIn.route
        ) {
            SignInScreen(navController)
        }
        composable(
            route = Screens.SignUp.route
        ) {
            SignUpScreen(navController)
        }
    }

}