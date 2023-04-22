package com.diplomproject.android.diplomaproject

import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.StructuredName
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.diplomproject.android.diplomaproject.screen.*

@Composable
fun Setup(
    navHostController: NavHostController,
    navController: String = Screen.StartApp.route,
    context: Context
) {
    NavHost(
        navController = navHostController,
        startDestination = navController
        ) {
        composable(
            route = Screen.StartApp.route
        ) {
            StartAppScreen(navHostController, context = context)
        }
        composable(
            route = Screen.SignIn.route
        ) {
            SignInScreen(navHostController)
        }
        composable(
            route = Screen.SignUp.route
        ) {
            SignUpScreen(navHostController)
        }
        composable(
            route = Screen.Menu.route
        ) {
            MenuScreen(navHostController, context = context)
        }
        composable(
            route = Screen.Create.route,
            arguments = listOf(navArgument("photoPath") { type = NavType.StringType })
        ) { backStackEntry ->
            val photoPath = backStackEntry.arguments?.getString(PHOTO_ARGUMENT).toString()
            CreateDocumentScreen(navHostController, photoPath)
        }
    }
}