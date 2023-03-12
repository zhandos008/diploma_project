package com.diplomproject.android.diplomaproject.screen

import android.graphics.BitmapFactory
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import java.io.File


@Composable
fun CreateDocumentScreen(navController: NavHostController, imagePath: String) {
    val bitmapFactory = BitmapFactory.decodeFile("/data/user/0/com.diplomproject.android.diplomaproject/files/"+imagePath)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(bitmap = bitmapFactory.asImageBitmap(), contentDescription = "newImage")
    }
}