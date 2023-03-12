package com.diplomproject.android.diplomaproject.screen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.diplomproject.android.diplomaproject.R
import com.diplomproject.android.diplomaproject.Screen
import java.io.Console
import java.io.File
import java.io.IOException

@Composable
fun MenuScreen(navController: NavHostController, context: Context) {
    val filePath = remember {
        mutableStateOf("")
    }

    val takePicture = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            navController.navigate(
                "create/" + filePath.value
            )

        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "My Screen") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { dispatchTakePictureIntent(takePicture, context, filePath) }) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        }
    ) {
        SearchItem(Modifier.padding(it))
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
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        }
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

