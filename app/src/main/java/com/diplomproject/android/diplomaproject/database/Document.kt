package com.diplomproject.android.diplomaproject.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Document(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val image: String
)