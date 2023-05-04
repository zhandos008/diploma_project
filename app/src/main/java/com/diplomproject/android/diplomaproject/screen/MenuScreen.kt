package com.diplomproject.android.diplomaproject.screen

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.diplomproject.android.diplomaproject.BuildConfig
import com.diplomproject.android.diplomaproject.R
import com.diplomproject.android.diplomaproject.Screen
import com.diplomproject.android.diplomaproject.database.CustomDocument
import com.google.firebase.auth.FirebaseAuth
import com.itextpdf.text.Document
import com.itextpdf.text.Font
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfPageEventHelper
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException



val REQUEST_CODE = 100



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
                title = { Text(text = "Click and Chill") },
                navigationIcon = {
                        SideMenuIcon(scope = scope, scaffoldState = scaffoldState)
                    },
                backgroundColor = Color.White
            )
        },
        drawerContent = {
            SideMenu(navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { dispatchTakePictureIntent(takePicture, context, filePath) },
                backgroundColor = Color.White,
                contentColor = Color(198, 105, 200)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }

        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchItem(Modifier.padding(it))
            Spacer(modifier = Modifier.height(20.dp))
            if(FirebaseAuth.getInstance()?.currentUser == null)
                LazyColumn() {

                    items(count = viewModel.documents.value.size) {
                        val item =  viewModel.documents.collectAsState().value.get(it)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Row(){
                                Text("${item.id} ", fontSize = 30.sp)

                                Text(item.name+" ", fontSize = 30.sp)
                            }

                            Row() {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Download Icon",
                                    modifier = Modifier
                                        .width(45.dp)
                                        .height(45.dp)
                                        .clickable(
                                            onClick = {
                                            }
                                        )
                                )

                                Icon(
                                    Icons.Default.ArrowDropDown ,
                                    contentDescription = "Delete Icon",
                                    modifier = Modifier
                                        .width(45.dp)
                                        .height(45.dp)
                                        .clickable(
                                            onClick = {
                                                viewModel.downloadPdf(item, context)
                                            }
                                        )
                                )
                            }

                        }


                    }
                }
            else {

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
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "${firebaseAuthEmail}",
            fontSize = 30.sp,
            modifier = Modifier.padding(25.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(20.dp)) {
            Text(text = "Settings",
                 fontSize = 25.sp,
                 modifier = Modifier.clickable(
                     onClick = {
                         navController.navigate(Screen.Settings.route)
                     }
                 )
            )
            Text(
                text = "Log out",
                color = Color.Red,
                fontSize = 25.sp,
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

