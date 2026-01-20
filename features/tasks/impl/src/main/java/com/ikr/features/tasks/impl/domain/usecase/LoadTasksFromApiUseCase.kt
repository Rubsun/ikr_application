package com.ikr.features.tasks.impl.domain.usecase

import com.ikr.features.tasks.impl.data.repository.TasksRepository
import com.ikr.features.tasks.impl.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * UseCase для загрузки задач из внешнего API
 */
internal class LoadTasksFromApiUseCase(
    private val repository: TasksRepository
) {
    suspend fun execute(): Flow<List<Task>> {
        return repository.loadTasksFromApi().map { dtos ->
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

