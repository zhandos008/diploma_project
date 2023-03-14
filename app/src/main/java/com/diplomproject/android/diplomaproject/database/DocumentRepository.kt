package com.diplomproject.android.diplomaproject.database

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.flow.Flow

private const val DATABASE_NAME = "document-database"

class DocumentRepository private constructor(context: Context){

    private val database: DocumentDataBase = Room
        .databaseBuilder(
            context.applicationContext,
            DocumentDataBase::class.java,
            DATABASE_NAME
        )
        .build()



    fun getDocuments(): Flow<List<Document>> = database.documentDao().getDocuments()

    suspend fun getDocument(id: Int): Document = database.documentDao().getDocument(id)

    suspend fun createDocument(document: Document) {
        database.documentDao().createDocument(document)
    }

    companion object {
        private var INSTANCE:DocumentRepository? = null

        fun initialize(context: Context) {
            if(INSTANCE == null) {
                INSTANCE = DocumentRepository(context)
            }
        }

        fun get(): DocumentRepository {
            return INSTANCE ?:
            throw IllegalStateException("DocumentRepository must be initialized")
        }

    }

}