package com.example.ikr_application.egorik4.data.models

import kotlinx.serialization.Serializable

@Serializable
internal data class OpenLibraryResponse(
    val docs: List<BookDto>
)

