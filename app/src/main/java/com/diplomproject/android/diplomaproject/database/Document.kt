package com.diplomproject.android.diplomaproject.database

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Document(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val text: String
)