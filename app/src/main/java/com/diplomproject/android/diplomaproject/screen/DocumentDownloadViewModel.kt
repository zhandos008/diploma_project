package com.diplomproject.android.diplomaproject.screen

import android.content.Context
import android.content.Intent
import android.os.Environment
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import com.diplomproject.android.diplomaproject.BuildConfig
import com.diplomproject.android.diplomaproject.database.CustomDocument
import com.diplomproject.android.diplomaproject.database.DocumentRepository
import com.itextpdf.text.Document
import com.itextpdf.text.Font
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfPageEventHelper
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream

class DocumentDownloadViewModel: ViewModel() {

    suspend fun getDocument(id: Int): CustomDocument{
        return DocumentRepository.get().getDocument(id)
    }

    fun downloadPdf(text: String, context: Context) {

        val fileName = "newDocument"
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



        val paragraph = Paragraph(text, font)
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