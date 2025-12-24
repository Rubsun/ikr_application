package com.example.ikr_application.michaelnoskov.data.api.model

data class SyncRequest(
    val localItems: List<LocalItemDto>,
    val lastSync: Long
)

data class LocalItemDto(
    val id: String,
    val text: String,
    val timestamp: Long
)