package com.diplomproject.android.diplomaproject.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavHostController
import com.diplomproject.android.diplomaproject.database.CustomDocument




@Composable
fun FileDownloadScreen(navController: NavHostController, context: Context,  text: String) {
    val viewModel = DocumentDownloadViewModel()


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(textAlign = TextAlign.Center, text = "Document.pdf", fontSize = 45.sp)
        Spacer(modifier = Modifier.fillMaxWidth().background(color = Color(0,0,0)).height(5.dp))
        Text(text = text, fontSize = 25.sp, modifier = Modifier
            .fillMaxWidth()
            .height(500.dp))
        Button(onClick = {
            viewModel.downloadPdf(text, context)
        }) {
            Text(text = "Download")
        }
    }



}