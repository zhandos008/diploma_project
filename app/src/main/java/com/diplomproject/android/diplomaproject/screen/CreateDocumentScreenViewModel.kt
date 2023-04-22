package com.diplomproject.android.diplomaproject.screen


import android.app.Activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController

import com.diplomproject.android.diplomaproject.Screen
import com.diplomproject.android.diplomaproject.database.Document
import com.diplomproject.android.diplomaproject.database.DocumentRepository

import kotlinx.coroutines.launch

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody


import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit


import okhttp3.OkHttpClient
import okhttp3.Request

import okhttp3.Response
import org.json.JSONArray

class CreateDocumentScreenViewModel: ViewModel() {
    private lateinit var documentRepository: DocumentRepository

    suspend fun addDocument(document: Document) {
        documentRepository = DocumentRepository.get()
        documentRepository.createDocument(document)
    }



    fun sendFileToFastAPI(file: File,  navController: NavHostController, activity: Activity):String {
        var result  = ""
         val client = OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()



         val MEDIA_TYPE_JPEG = "image/jpeg".toMediaTypeOrNull()

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "file",
                file.name,
                file.asRequestBody(MEDIA_TYPE_JPEG)
            )
            .build()

        val request = Request.Builder()
            .url("http://192.168.0.17:8000/model")
            .post(requestBody)
            .build()



         client.newCall(request).enqueue(object : Callback {
             override fun onResponse(call: Call, response: Response) {
                 if (response.isSuccessful) {
                     println("Файл успешно отправлен в FastAPI")
                     val responseBody = response.body?.string()
                     val jsonArray = JSONArray(responseBody)
                     for (i in 0 until jsonArray.length()) {
                         val subArray = jsonArray.getJSONArray(i)
                         result += subArray.getString(1) + " "
                     }

                     val document = createDocument(result)

                     activity.runOnUiThread {
                         viewModelScope.launch {
                             addDocument(document)
                         }

                         navController.navigate(
                             Screen.Menu.route
                         ) {
                             popUpTo(Screen.Create.route) {
                                 inclusive = true
                                 saveState = true
                             }
                         }
                     }


                 } else {
                     println("Не удалось отправить файл в FastAPI")
                 }
             }

             override fun onFailure(call: Call, e: IOException) {
                 println("Ошибка отправки файла в FastAPI: $e")
             }
         })

         return result
    }



    fun createDocument(text: String): Document {
        return Document(name = "photo", text = text)
    }

}