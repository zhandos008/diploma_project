package com.diplomproject.android.diplomaproject.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.diplomproject.android.diplomaproject.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SignInScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
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

        SignInItems(navController, context)
        Spacer(
            modifier = Modifier
                .padding(bottom = 100.dp)
                .clickable { },
        )

    }
}

@Composable
fun SignInItems(navController: NavHostController,context: Context, modifier: Modifier = Modifier) {
    val login = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(bottom = 100.dp),
        
    ) {
        TextField(value = login.value , onValueChange = {login.value  = it}, label = {Text("E-mail")},
            modifier = modifier.padding(bottom = 10.dp).height(60.dp) )
        TextField(value = password.value, onValueChange = { password.value = it },  label = {Text("password")},
            modifier = modifier.height(60.dp), visualTransformation = PasswordVisualTransformation())
        Button(
            onClick = {
                val user = FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(login.value, password.value)
                    .addOnSuccessListener { authResult ->
                        navController.navigate(
                            Screen.Menu.route
                        ) {
                            popUpTo(Screen.SignIn.route) {
                                inclusive = true
                                saveState = true
                            }
                        }
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(context, "User doesn't exist", Toast.LENGTH_SHORT).show()
                    }
            },
            modifier = modifier
                .width(150.dp)
                .padding(top = 15.dp)
                .clip(RoundedCornerShape(16.dp)),
        ) {
            Text(text = "Sign In")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignIn() {
    SignInScreen(rememberNavController())
}