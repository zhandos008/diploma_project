package com.diplomproject.android.diplomaproject.screen

import androidx.lifecycle.ViewModel
import com.diplomproject.android.diplomaproject.database.Document
import com.diplomproject.android.diplomaproject.database.DocumentRepository

class CreateDocumentScreenViewModel: ViewModel() {
    private val documentRepository = DocumentRepository.get()

    suspend fun addDocument(document: Document) {
        documentRepository.createDocument(document)
    }

    fun createDocument(photoName: String): Document {
        return Document(name = "photo", image = photoName)
    }

}