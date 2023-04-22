package com.diplomproject.android.diplomaproject.screen

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


import kotlinx.coroutines.*
import java.io.File


//"/data/user/0/com.diplomproject.android.diplomaproject/files/"
@Composable
fun CreateDocumentScreen(navController: NavHostController, photoName: String) {
    val viewModel = viewModel<CreateDocumentScreenViewModel>()
    val photoPath = LocalContext.current.filesDir.canonicalPath+ "/" + photoName
    val bitmapFactory = BitmapFactory.decodeFile(photoPath)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(bitmap = bitmapFactory.asImageBitmap(), contentDescription = "newImage")
        ChooseItems(photoName, viewModel, navController, photoPath)
    }
}

@Composable
fun ChooseItems(photo: String, viewModel: CreateDocumentScreenViewModel, navController: NavHostController, photoPath: String) {
    val options = listOf("english", "russia", "kazakh")
    val selectedOption = remember { mutableStateOf(options[0]) }
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Row() {
        Button(onClick = { /*TODO*/ }) {
          Text(text = "retake")  
        }
        Box{
            Text(
                text = "${selectedOption.value}",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable(onClick = { expanded = true })
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            selectedOption.value = option
                            expanded = false
                        }
                    ) {
                        Text(text = option)
                    }
                }
            }
        }
        Button(onClick = {
            createDocument(photo = photo, viewModel = viewModel, navController, photoPath, context)
        }) {
            Text(text = "create")
        }
        
        
    }
}

fun createDocument(photo: String, viewModel: CreateDocumentScreenViewModel, navController: NavHostController, photoPath: String, context: Context) {
    val document = viewModel.createDocument(photo)

    println("Start ml")
    viewModel.viewModelScope.launch {
//        viewModel.addDocument(document)
//        viewModel.requestApi(filePath = photoPath)
        viewModel.sendFileToFastAPI(File(photoPath))
    }


//     var res = viewModel.workWithImage(context, File(photoPath))



//    navController.navigate(
//        Screen.Menu.route
//    ) {
//        popUpTo(Screen.Create.route) {
//            inclusive = true
//        }
//    }


}

@Preview(showBackground = true)
@Composable
fun PreviewCreateDocumentScreen() {
    CreateDocumentScreen(navController = rememberNavController(), photoName = "")
}

