package com.diplomproject.android.diplomaproject.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.*


@Dao
interface DocumentDao {

    @Query("SELECT * FROM document")
    fun getDocuments(): Flow<List<Document>>

    @Query("SELECT * FROM document WHERE id=(:id)")
    suspend fun getDocument(id: Int): Document

    @Insert
    suspend fun createDocument(document: Document)

}