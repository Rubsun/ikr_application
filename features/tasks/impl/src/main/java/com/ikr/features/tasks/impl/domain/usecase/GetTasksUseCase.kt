package com.ikr.features.tasks.impl.domain.usecase

import com.ikr.features.tasks.impl.data.repository.TasksRepository
import com.ikr.features.tasks.impl.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * UseCase для получения списка задач
 */
internal class GetTasksUseCase(
    private val repository: TasksRepository
) {
    fun execute(): Flow<List<Task>> {
        return repository.getTasks().map { dtos ->
            dtos.map { dto ->
                Task(
                    id = dto.id,
                    title = dto.title,
                    description = dto.description,
                    completed = dto.completed,
                    imageUrl = dto.imageUrl,
                    createdAt = dto.createdAt
                )
            }
        }
    }
}

