package com.diplomproject.android.diplomaproject.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.*


@Dao
interface DocumentDao {

    @Query("SELECT * FROM customDocument")
    fun getDocuments(): Flow<List<CustomDocument>>

    @Query("SELECT * FROM customDocument WHERE id=(:id)")
    suspend fun getDocument(id: Int): CustomDocument

    @Insert
    suspend fun createDocument(document: CustomDocument)

}