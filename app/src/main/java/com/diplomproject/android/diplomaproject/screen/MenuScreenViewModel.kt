package com.diplomproject.android.diplomaproject.screen

import android.content.Context
import android.content.Intent
import android.os.Environment
import androidx.compose.runtime.mutableStateListOf
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diplomproject.android.diplomaproject.BuildConfig
import com.diplomproject.android.diplomaproject.database.CustomDocument
import com.diplomproject.android.diplomaproject.database.DocumentRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.itextpdf.text.Document
import com.itextpdf.text.Font
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfPageEventHelper
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class MenuScreenViewModel: ViewModel() {
    private val documentRepository = DocumentRepository.get()

    private val _documents: MutableStateFlow<List<CustomDocument>> = MutableStateFlow(emptyList())
    val documents: StateFlow<List<CustomDocument>> = _documents.asStateFlow()

    private val usersRef = Firebase.database.reference.child("User")
    private var _documentsFire:  MutableStateFlow<List<CustomDocument>> = MutableStateFlow(emptyList())


    val documentsFire: StateFlow<List<CustomDocument>> = _documentsFire

    init {
        viewModelScope.launch {
            documentRepository.getDocuments().collect() {
                _documents.value = it
            }
        }

        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val documentList = mutableListOf<CustomDocument>()
                for (userSnapshot in dataSnapshot.children) {
                    val userName = userSnapshot.child("name").getValue(String::class.java) ?: ""
                    val userText = userSnapshot.child("text").getValue(String::class.java) ?: ""
                    val user = CustomDocument(name = userName, text = userText)
                    documentList.add(user)
                }
                _documentsFire.value = documentList
                println(_documentsFire.value.get(0).text)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })


    }

    fun downloadPdf(documentForDownload: CustomDocument, context: Context) {

        val fileName = "" + documentForDownload.id+  "-"+ documentForDownload.name
        val filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + fileName
        val outputStream = FileOutputStream(filePath)

        val document = Document()
        PdfWriter.getInstance(document, outputStream).apply {
            setPageEvent(PdfPageEventHelper())
            setInitialLeading(16f)
            setCompressionLevel(0)
        }

        document.open()

        val baseFont = BaseFont.createFont("assets/fonts/ofont.ru_Arial Cyr.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED)
        val font = Font(baseFont, 12f)



        val paragraph = Paragraph(documentForDownload.text, font)
        document.add(paragraph)

        document.close()
        outputStream.close()

        val file = File(filePath)
        val uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", file)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri, "application/pdf")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        val chooser = Intent.createChooser(intent, "Open with")
        context.startActivity(chooser)
    }

}