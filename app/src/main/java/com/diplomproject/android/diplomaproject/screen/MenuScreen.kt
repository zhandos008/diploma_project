package com.diplomproject.android.diplomaproject.screen

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.diplomproject.android.diplomaproject.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
//            navController.navigate(
//                "create/" + filePath.value
//            )

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
        Column {
            SearchItem(Modifier.padding(it))
            LazyColumn() {

                items(count = viewModel.documents.value.size) {
                    val item =  viewModel.documents.collectAsState().value.get(it)

                    Text("${item.id}")

                    Image(
                        bitmap = rememberImageBitmapFromCoroutine(context = LocalContext.current, imagePath = item.image),
                        contentDescription = "sdsa",
                        modifier = Modifier
                            .width(350.dp)
                            .height(150.dp)
                    )
                }
            }
        }


    }
}



@Composable
fun rememberImageBitmapFromCoroutine(context: Context, imagePath: String): ImageBitmap {
    val imageBitmapState = remember { mutableStateOf<ImageBitmap?>(null) }
    val ImageBitmapStub = ImageBitmap(1, 1)
    LaunchedEffect(imagePath) {
        withContext(Dispatchers.IO) {
            val imageBitmap = BitmapFactory.decodeFile(context.filesDir.canonicalPath + "/" + imagePath).asImageBitmap()
            imageBitmapState.value = imageBitmap
        }
    }
    return imageBitmapState.value ?: ImageBitmapStub
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

