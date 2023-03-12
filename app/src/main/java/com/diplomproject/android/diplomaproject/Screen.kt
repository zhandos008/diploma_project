package com.diplomproject.android.diplomaproject

const val PHOTO_ARGUMENT = "photoPath"
sealed class Screen(val route: String) {
    object StartApp: Screen("start_app_screen")
    object SignIn: Screen("sign_in_screen")
    object SignUp: Screen("sign_up_screen")
    object Menu: Screen("menu_screen")
    object Create: Screen("create//data/user/0/com.diplomproject.android.diplomaproject/files/{photoPath}") {

    }
}