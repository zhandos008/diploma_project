package com.diplomproject.android.diplomaproject


import android.app.Application
import com.diplomproject.android.diplomaproject.database.DocumentRepository

class DocumentIntentApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        DocumentRepository.initialize(this)
//        FirebaseApp.initializeApp(this)
    }
}