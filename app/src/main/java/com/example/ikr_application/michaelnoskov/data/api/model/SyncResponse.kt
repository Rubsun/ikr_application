package com.example.ikr_application.michaelnoskov.data.api.model

data class SyncResponse(
    val remoteItems: List<RemoteItemResponse>,
    val syncTimestamp: Long
)