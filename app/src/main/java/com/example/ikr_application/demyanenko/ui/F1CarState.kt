package com.example.ikr_application.demyanenko.ui

import com.example.ikr_application.demyanenko.data.F1Car

data class F1CarState(
    val cars: List<F1Car> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = ""
)
