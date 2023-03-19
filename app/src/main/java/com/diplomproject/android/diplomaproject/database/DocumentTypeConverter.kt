package com.diplomproject.android.diplomaproject.database

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.io.ByteArrayOutputStream


class DocumentTypeConverter {

    @TypeConverter
    fun fromBitMap(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 1, stream)
        return stream.toByteArray()
    }

    @TypeConverter
    fun toBitMap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }



}