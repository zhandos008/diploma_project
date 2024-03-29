package com.diplomproject.android.diplomaproject.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.diplomproject.android.diplomaproject.Screen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SettingsScreen(navController: NavHostController) {
    val email = remember { mutableStateOf("${FirebaseAuth.getInstance().currentUser?.email}") }

    val dialogState = remember { mutableStateOf(false) }
    val confirmDeleteAccount = {
        FirebaseAuth.getInstance().currentUser?.delete()
    }
    if (dialogState.value) {
        AlertDialog(
            onDismissRequest = { dialogState.value = false },
            title = { Text("Delete account?") },
            text = { Text("Are you sure you want to delete your account?") },
            confirmButton = {
                Button(
                    onClick = {
                        confirmDeleteAccount()
                        dialogState.value = false
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                Button(
                    onClick = { dialogState.value = false }
                ) {
                    Text("Cancel")
                }
            },
            modifier = Modifier.padding(16.dp),
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopAppBar(
            title = { Text(text = "Settings") },
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigate(Screen.Menu.route) {
                        popUpTo(Screen.StartApp.route) {
                            inclusive = true
                            saveState = true
                        }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            backgroundColor = Color.White
        )
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "E-address", fontSize = 25.sp, modifier = Modifier.weight(1f))
                Text(text = email.value, fontSize = 20.sp, modifier = Modifier.weight(1f))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "password", fontSize = 25.sp, modifier = Modifier.weight(1f))
                Text(text = "********", fontSize = 20.sp, modifier = Modifier.weight(1f))
            }
            Text(text = "Clear Cache",
                fontSize = 25.sp,
                modifier = Modifier.padding(10.dp),
                color = Color(198, 105, 200))
        }

        Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Log out",
                fontSize = 25.sp,
                modifier = Modifier.padding(4.dp))
            Text(text = "Delete account",
                fontSize = 25.sp,
                modifier = Modifier
                    .padding(4.dp)
                    .clickable(
                        onClick = {
                            dialogState.value = true

                        }
                    ), color = Color.Red)
        }
    }
}




@Preview(showBackground = true)
@Composable
fun PreviewSettingsScreen() {
    SettingsScreen(navController = rememberNavController())
}