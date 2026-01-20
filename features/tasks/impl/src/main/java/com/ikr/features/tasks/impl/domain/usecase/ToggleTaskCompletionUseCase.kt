package com.ikr.features.tasks.impl.domain.usecase

import com.ikr.features.tasks.impl.data.dto.TaskDto
import com.ikr.features.tasks.impl.data.repository.TasksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * UseCase для переключения статуса выполнения задачи
 */
internal class ToggleTaskCompletionUseCase(
    private val repository: TasksRepository
) {
    suspend fun execute(taskId: String) {
        withContext(Dispatchers.IO) {
            val task = repository.getTaskById(taskId)
            task?.let {
                repository.updateTask(it.copy(completed = !it.completed))
            }
        }
    }
}

