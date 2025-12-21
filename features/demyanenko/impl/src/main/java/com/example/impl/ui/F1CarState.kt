package com.example.impl.ui

import com.example.impl.data.F1Car


internal data class F1CarState(
    val cars: List<F1Car> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = ""
)
