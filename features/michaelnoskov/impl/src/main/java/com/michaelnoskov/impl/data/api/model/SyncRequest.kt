package com.michaelnoskov.impl.data.api.model

internal data class SyncRequest(
    val localItems: List<LocalItemDto>,
    val lastSync: Long
)

internal data class LocalItemDto(
    val id: String,
    val text: String,
    val timestamp: Long
)

