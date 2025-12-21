package com.dimmension.impl.domain

import com.dimmension.api.domain.models.NameDisplayModel
import com.dimmension.impl.data.models.NameRecordDto

internal fun NameRecordDto.toDomain(): NameDisplayModel {
    val lastInitial = lastName.firstOrNull()?.let { "$it." } ?: ""

    val shortName = listOf(firstName, lastInitial)
        .filter { it.isNotEmpty() }
        .joinToString(" ")

    return NameDisplayModel(
        displayName = fullName,
        shortName = shortName,
        initials = initials,
    )
}


