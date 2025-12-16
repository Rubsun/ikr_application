package com.example.ikr_application.egorik4.data.models

import kotlinx.serialization.Serializable

@Serializable
internal data class BookDto(
    val title: String? = null,
    val author_name: List<String>? = null,
    val first_publish_year: Int? = null,
    val cover_i: Long? = null,
    val key: String? = null
)

