package com.diplomproject.android.diplomaproject.Screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.diplomproject.android.diplomaproject.Screens
import com.diplomproject.android.diplomaproject.ui.theme.DiplomaProjectTheme


@Composable
fun StartAppScreen(
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Click and Chill",
            modifier = Modifier
                .padding(
                    top = 200.dp,
                ),
            style = MaterialTheme.typography.h3
        )
        AuthorizationButtons(navController)
        Text(
            text = "Continue as Guest",
            modifier = Modifier
                .padding(bottom = 100.dp)
                .clickable { },
            textDecoration = TextDecoration.Underline

        )

    }
}

@Composable
fun AuthorizationButtons(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(bottom = 100.dp)
    ) {
        Button(
            onClick = {
                navController.navigate(Screens.SignIn.route)
            },
            modifier = modifier.width(150.dp)
        ) {
            Text(text = "Sign In")
        }
        Button(
            onClick = {
                navController.navigate(Screens.SignUp.route)
            },
            modifier = modifier.width(150.dp)
        ) {
            Text(text = "Sign Up")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DiplomaProjectTheme {
        StartAppScreen(rememberNavController())
    }
}