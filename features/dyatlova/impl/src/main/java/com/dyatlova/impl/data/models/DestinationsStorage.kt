package com.dyatlova.impl.data.models

import kotlinx.serialization.Serializable

@Serializable
internal data class DestinationsStorage(
    val destinations: List<DestinationData> = emptyList(),
)



