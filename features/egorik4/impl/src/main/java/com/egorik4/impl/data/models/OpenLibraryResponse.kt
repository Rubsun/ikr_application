package com.egorik4.impl.data.models

import kotlinx.serialization.Serializable

@Serializable
internal data class OpenLibraryResponse(
    val docs: List<BookDto>
)
