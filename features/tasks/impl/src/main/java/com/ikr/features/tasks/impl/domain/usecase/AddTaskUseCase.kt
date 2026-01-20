package com.ikr.features.tasks.impl.domain.usecase

import com.ikr.features.tasks.impl.data.repository.TasksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * UseCase для добавления новой задачи
 */
internal class AddTaskUseCase(
    private val repository: TasksRepository
) {
    suspend fun execute(title: String, description: String) {
        withContext(Dispatchers.IO) {
            repository.addTask(title, description)
        }
    }
}

