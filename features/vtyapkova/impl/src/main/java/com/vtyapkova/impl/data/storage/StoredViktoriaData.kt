package com.vtyapkova.impl.data.storage

import kotlinx.serialization.Serializable

@Serializable
internal data class StoredViktoriaData(
    val firstName: String,
    val lastName: String,
    val fullName: String,
    val initials: String,
)
