package com.diplomproject.android.diplomaproject.screen

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.diplomproject.android.diplomaproject.database.Document
import com.diplomproject.android.diplomaproject.database.DocumentRepository

class CreateDocumentScreenViewModel: ViewModel() {
    private lateinit var documentRepository: DocumentRepository

    suspend fun addDocument(document: Document) {
        documentRepository = DocumentRepository.get()
        documentRepository.createDocument(document)
    }

    fun createDocument(photo: String): Document {
        return Document(name = "photo", image = photo)
    }

}