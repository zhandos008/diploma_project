package com.diplomproject.android.diplomaproject

import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.StructuredName
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.diplomproject.android.diplomaproject.screen.*

@Composable
fun Setup(
    navController: NavHostController,
    context: Context
) {
    NavHost(
        navController = navController,
        startDestination = Screen.StartApp.route
        ) {
        composable(
            route = Screen.StartApp.route
        ) {
            StartAppScreen(navController, context = context)
        }
        composable(
            route = Screen.SignIn.route
        ) {
            SignInScreen(navController)
        }
        composable(
            route = Screen.SignUp.route
        ) {
            SignUpScreen(navController)
        }
        composable(
            route = Screen.Menu.route
        ) {
            MenuScreen(navController, context = context)
        }
        composable(
            route = Screen.Create.route,
            arguments = listOf(navArgument("photoPath") { type = NavType.StringType })
        ) { backStackEntry ->
            val photoPath = backStackEntry.arguments?.getString(PHOTO_ARGUMENT).toString()
            CreateDocumentScreen(navController, photoPath)
        }
    }
}