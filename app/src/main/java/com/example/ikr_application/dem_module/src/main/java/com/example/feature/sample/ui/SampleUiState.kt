package com.example.ikr_application.dem_module.ui
import com.example.ikr_application.dem_module.domain.SampleItem
sealed class SampleUiState {
    data object Loading : SampleUiState()
    data class Success(val items: List<SampleItem>) : SampleUiState()
    data class Error(val message: String) : SampleUiState()
    data object Empty : SampleUiState()
}
