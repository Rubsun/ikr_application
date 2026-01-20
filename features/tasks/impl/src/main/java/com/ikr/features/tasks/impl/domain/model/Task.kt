package com.ikr.features.tasks.impl.domain.model

/**
 * Domain модель задачи (обычные данные)
 */
internal data class Task(
    val id: String,
    val title: String,
    val description: String,
    val completed: Boolean,
    val imageUrl: String?,
    val createdAt: Long
)

