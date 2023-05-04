package com.diplomproject.android.diplomaproject.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [CustomDocument::class], version = 1)
abstract class DocumentDataBase: RoomDatabase() {
    abstract fun documentDao():DocumentDao
}