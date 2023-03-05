package com.diplomproject.android.diplomaproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diplomproject.android.diplomaproject.ui.theme.DiplomaProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiplomaProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    AppScreen()
                }
            }
        }
    }
}

@Composable
fun AppScreen() {
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
        AuthorizationButtons()
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
fun AuthorizationButtons(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(bottom = 100.dp)
    ) {
        Button(
            onClick = {  },
            modifier = modifier.width(150.dp)
        ) {
            Text(text = "Sign In")
        }
        Button(
            onClick = { },
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
        AppScreen()
    }
}