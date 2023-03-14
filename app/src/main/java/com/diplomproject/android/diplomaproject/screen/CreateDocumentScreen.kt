package com.diplomproject.android.diplomaproject.screen

import android.graphics.BitmapFactory
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.diplomproject.android.diplomaproject.Screen
import kotlinx.coroutines.launch
import java.io.File


@Composable
fun CreateDocumentScreen(navController: NavHostController, photoName: String) {
    val viewModel = viewModel<CreateDocumentScreenViewModel>()
    val bitmapFactory = BitmapFactory.decodeFile("/data/user/0/com.diplomproject.android.diplomaproject/files/" + photoName)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(bitmap = bitmapFactory.asImageBitmap(), contentDescription = "newImage")
        chooseItems(photoName, viewModel, navController)
    }
}

@Composable
fun chooseItems(photoName: String, viewModel: CreateDocumentScreenViewModel, navController: NavHostController) {
    val options = listOf("english", "russia", "kazakh")
    val selectedOption = remember { mutableStateOf(options[0]) }
    var expanded by remember { mutableStateOf(false) }
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
            createDocument(photoName = photoName, viewModel = viewModel, navController)
        }) {
            Text(text = "create")
        }
        
        
    }
}

fun createDocument(photoName: String, viewModel: CreateDocumentScreenViewModel, navController: NavHostController) {
    val document = viewModel.createDocument(photoName)
    viewModel.viewModelScope.launch {
        viewModel.addDocument(document)
    }

    navController.navigate(
        Screen.Menu.route
    )

}

@Preview(showBackground = true)
@Composable
fun PreviewCreateDocumentScreen() {
    CreateDocumentScreen(navController = rememberNavController(), photoName = "")
}

