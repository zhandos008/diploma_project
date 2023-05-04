package com.diplomproject.android.diplomaproject.screen


import android.app.Activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController

import com.diplomproject.android.diplomaproject.Screen
import com.diplomproject.android.diplomaproject.database.CustomDocument
import com.diplomproject.android.diplomaproject.database.DocumentRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

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

    suspend fun addDocument(document: CustomDocument) {
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
            .url("http://192.168.0.11:8000/model")
            .post(requestBody)
            .build()



         client.newCall(request).enqueue(object : Callback {
             override fun onResponse(call: Call, response: Response) {
                 if (response.isSuccessful) {
                     println("success")
                     val responseBody = response.body?.string()
                     val jsonArray = JSONArray(responseBody)
                     for (i in 0 until jsonArray.length()) {
                         val subArray = jsonArray.getJSONArray(i)
                         result += subArray.getString(1) + " "
                     }

                     val document = createDocument(result)

                     activity.runOnUiThread {
                         viewModelScope.launch {
                             if(FirebaseAuth.getInstance().currentUser == null) {
                                 addDocument(document)
                             }
                             else {
                                 val dataBase = Firebase.database.reference

                                 dataBase.child("User").child("document").setValue(document)
                             }
                         }

                         navController.navigate(
                             "download_screen/" + document.text
                         ) {
                             popUpTo(Screen.Create.route) {
                                 inclusive = true
                                 saveState = true
                             }
                         }
                     }


                 } else {
                     println("doesn't success")
                 }
             }

             override fun onFailure(call: Call, e: IOException) {
                 println("error: $e")
             }
         })

         return result
    }



    fun createDocument(text: String): CustomDocument {
        return CustomDocument(name = "document.pdf", text = text)
    }

}