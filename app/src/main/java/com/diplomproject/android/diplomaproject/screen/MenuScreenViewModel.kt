package com.diplomproject.android.diplomaproject.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diplomproject.android.diplomaproject.database.Document
import com.diplomproject.android.diplomaproject.database.DocumentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MenuScreenViewModel: ViewModel() {
    private val documentRepository = DocumentRepository.get()

    private val _documents: MutableStateFlow<List<Document>> = MutableStateFlow(emptyList())
    val documents: StateFlow<List<Document>> = _documents.asStateFlow()

    init {
        viewModelScope.launch {
            documentRepository.getDocuments().collect() {
                _documents.value = it
            }
        }
    }

}