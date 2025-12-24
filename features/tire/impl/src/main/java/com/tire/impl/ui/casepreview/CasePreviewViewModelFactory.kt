package com.tire.impl.ui.casepreview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

internal class CasePreviewViewModelFactory(
    private val caseId: String
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == CasePreviewViewModel::class.java)
        return CasePreviewViewModel(caseId) as T
    }
}
