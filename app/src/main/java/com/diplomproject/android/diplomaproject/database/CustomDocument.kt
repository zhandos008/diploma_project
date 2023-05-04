package com.diplomproject.android.diplomaproject.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CustomDocument(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val text: String
)