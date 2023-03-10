package com.diplomproject.android.diplomaproject

sealed class Screens(val route: String) {
    object StartApp: Screens("start_app_screen")
    object SignIn: Screens("sign_in_screen")
    object SignUp: Screens("sign_up_screen")
}