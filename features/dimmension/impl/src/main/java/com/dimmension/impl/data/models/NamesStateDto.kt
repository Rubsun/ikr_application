package com.dimmension.impl.data.models

import kotlinx.serialization.Serializable

@Serializable
internal data class NamesStateDto(
    val names: List<NameRecordDto> = emptyList(),
)


