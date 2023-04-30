package com.diplomproject.android.diplomaproject.screen

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.diplomproject.android.diplomaproject.R
import com.diplomproject.android.diplomaproject.Screen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException


@Composable
fun MenuScreen(navController: NavHostController, context: Context) {
    val viewModel = viewModel<MenuScreenViewModel>()
    val filePath = remember {
        mutableStateOf("")
    }

    val takePicture = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            Log.i("Testing", filePath.value)
            navController.navigate(
                "create/" + filePath.value
            )

        }
    }


    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "My Screen") },
                navigationIcon = {
                        SideMenuIcon(scope = scope, scaffoldState = scaffoldState)
                    }
            )
        },
        drawerContent = {
            SideMenu(navController)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { dispatchTakePictureIntent(takePicture, context, filePath) }) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        }
    ) {
        Column {
            SearchItem(Modifier.padding(it))
            LazyColumn() {

                items(count = viewModel.documents.value.size) {
                    val item =  viewModel.documents.collectAsState().value.get(it)

                    Text("${item.id}")

                    Text("${item.text}")
                }
            }
        }


    }
}

@Composable
fun SideMenuIcon(scope: CoroutineScope, scaffoldState: ScaffoldState) {
    IconButton(
        onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            },
        ) {
        Icon(
            imageVector = Icons.Filled.Menu,
            contentDescription = "Open Navigation Drawer"
        )
    }
}



@Composable
fun SideMenu(navController: NavHostController) {
    val firebaseAuthEmail = FirebaseAuth.getInstance().currentUser?.email
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "${firebaseAuthEmail}")
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier) {
            Text(text = "Settings",
                 modifier = Modifier.clickable(
                     onClick = {
                         navController.navigate(Screen.Settings.route)
                     }
                 ))
            Text(
                text = "Log out",
                color = Color.Red,
                modifier = Modifier.clickable(
                    onClick = {
                        FirebaseAuth.getInstance().signOut()
                        navController.navigate(Screen.StartApp.route) {
                            popUpTo(Screen.StartApp.route) {
                                inclusive = true
                                saveState = true
                            }
                        }
                    }
                )
            )
        }

    }


}







@Composable
 fun SearchItem(modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.search_icon),
            contentDescription = "search",
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
        )
        TextField(
            value = "",
            onValueChange = {},
            label = {Text("search")},
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
        )
    }
}


fun dispatchTakePictureIntent(
    takePicture: ManagedActivityResultLauncher<Uri, Boolean>,
    context: Context,
    filePath: MutableState<String>
) {
    val photoFile: File? = try {
        createImageFile(context)
    } catch (ex: IOException) {
        null
    }
    photoFile?.also {
        val photoUri: Uri = FileProvider.getUriForFile(
            context,
            "com.diplomproject.android.diplomaproject.fileprovider",
            it
        )

        takePicture.launch(photoUri)
    }
    filePath.value = photoFile?.absolutePath ?: ""
}

fun createImageFile(context: Context): File {
    var outputDirectory: File = context.getFilesDir()
    if (!outputDirectory.exists()) {
        outputDirectory.mkdirs()
    }
    return File.createTempFile(
        "JPEG_${System.currentTimeMillis()}_",
        ".jpg",
        outputDirectory
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewMenuScreen() {
//    MenuScreen(rememberNavController())
}

