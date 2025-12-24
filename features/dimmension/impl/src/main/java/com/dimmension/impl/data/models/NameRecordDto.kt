package com.dimmension.impl.data.models

import kotlinx.serialization.Serializable

@Serializable
internal data class NameRecordDto(
    val firstName: String,
    val lastName: String,
) {
    val fullName: String
        get() = listOf(firstName, lastName)
            .filter { it.isNotBlank() }
            .joinToString(" ")

    val initials: String
        get() {
            val firstInitial = firstName.firstOrNull()?.let { "$it." } ?: ""
            val lastInitial = lastName.firstOrNull()?.let { "$it." } ?: ""
            return listOf(firstInitial, lastInitial)
                .filter { it.isNotEmpty() }
                .joinToString(" ")
        }
}


