package com.ikr.features.tasks.impl.domain.usecase

import com.ikr.features.tasks.impl.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

/**
 * UseCase для фильтрации задач по поисковому запросу
 */
internal class FilterTasksUseCase {
    fun execute(tasks: Flow<List<Task>>, searchQuery: Flow<String>): Flow<List<Task>> {
        return combine(tasks, searchQuery) { taskList, query ->
            if (query.isBlank()) {
                taskList
            } else {
                val lowerQuery = query.lowercase()
                taskList.filter {
                    it.title.lowercase().contains(lowerQuery) ||
                    it.description.lowercase().contains(lowerQuery)
                }
            }
        }
    }
}

