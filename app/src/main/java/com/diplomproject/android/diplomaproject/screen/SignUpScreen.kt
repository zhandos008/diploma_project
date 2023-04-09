package com.diplomproject.android.diplomaproject.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.diplomproject.android.diplomaproject.Screen
import com.google.firebase.auth.FirebaseAuth


@Composable
fun SignUpScreen(
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
        SignUpItems(navController)
        Spacer(
            modifier = Modifier
                .padding(bottom = 100.dp)
                .clickable { },
        )

    }
}

@Composable
fun SignUpItems(navController: NavHostController, modifier: Modifier = Modifier) {
    var login = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    var repeatablePassword = remember { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(bottom = 100.dp),

        ) {
        TextField(value = login.value ,
            onValueChange = {login.value  = it},
            label = {Text("login")},
            modifier = modifier
                .padding(bottom = 10.dp)
                .height(50.dp) )
        TextField(value = password.value,
            onValueChange = { password.value = it },
            label = {Text("password")},
            modifier = modifier
                .padding(bottom = 10.dp)
                .height(50.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        TextField(value = repeatablePassword.value,
            onValueChange = { repeatablePassword.value = it },
            label = {Text("confirm password")},
            modifier = modifier.height(50.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(login.value, password.value)
            },
            modifier = modifier
                .width(150.dp)
                .padding(top = 15.dp)
        ) {
            Text(text = "Sign In")
        }
    }
}

