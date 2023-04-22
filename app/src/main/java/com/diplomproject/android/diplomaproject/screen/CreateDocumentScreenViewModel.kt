package com.diplomproject.android.diplomaproject.screen


import androidx.lifecycle.ViewModel
import com.diplomproject.android.diplomaproject.database.Document
import com.diplomproject.android.diplomaproject.database.DocumentRepository

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody


import java.io.File
import java.io.IOException


class CreateDocumentScreenViewModel: ViewModel() {
    private lateinit var documentRepository: DocumentRepository

    suspend fun addDocument(document: Document) {
        documentRepository = DocumentRepository.get()
        documentRepository.createDocument(document)
    }



     fun sendFileToFastAPI(file: File) {
        val client = OkHttpClient()

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
            .url("http://192.168.0.19:8080/model")
            .post(requestBody)
            .build()

         client.newCall(request).enqueue(object : Callback {
             override fun onResponse(call: Call, response: Response) {
                 if (response.isSuccessful) {
                     println("Файл успешно отправлен в FastAPI"+ response.body?.string())
                 } else {
                     println("Не удалось отправить файл в FastAPI")
                 }
             }

             override fun onFailure(call: Call, e: IOException) {
                 println("Ошибка отправки файла в FastAPI: $e")
             }
         })
    }




    fun createDocument(photo: String): Document {
        return Document(name = "photo", image = photo)
    }

}