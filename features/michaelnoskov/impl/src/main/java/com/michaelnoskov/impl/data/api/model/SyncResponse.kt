package com.michaelnoskov.impl.data.api.model

internal data class SyncResponse(
    val remoteItems: List<RemoteItemResponse>,
    val syncTimestamp: Long
)

