package com.ikr.features.tasks.impl.data.dto

import kotlinx.serialization.Serializable

/**
 * DTO для задачи (сырые данные от API)
 */
@Serializable
internal data class TaskDto(
    val id: String,
    val title: String,
    val description: String,
    val completed: Boolean,
    val imageUrl: String? = null,
    val createdAt: Long
)

