package com.ikr.features.tasks.impl.ui.state

import com.ikr.features.tasks.impl.domain.model.Task

/**
 * UI State для экрана задач
 */
internal data class TasksUiState(
    val tasks: List<Task> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

