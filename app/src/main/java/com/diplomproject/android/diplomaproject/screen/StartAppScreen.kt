package com.diplomproject.android.diplomaproject.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.diplomproject.android.diplomaproject.Screen
import com.diplomproject.android.diplomaproject.ui.theme.DiplomaProjectTheme


@Composable
fun StartAppScreen(
    navController: NavHostController,
    context: Context
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
                .clickable {
                    navController.navigate(Screen.Menu.route)
                },
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
                navController.navigate(Screen.SignIn.route)
            },
            modifier = modifier
                .width(150.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            Text(text = "Sign In")
        }
        Button(
            onClick = {
                navController.navigate(Screen.SignUp.route)
            },
            modifier = modifier.width(150.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            Text(
                text = "Sign Up",
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DiplomaProjectTheme {
//        StartAppScreen(rememberNavController())
    }
}