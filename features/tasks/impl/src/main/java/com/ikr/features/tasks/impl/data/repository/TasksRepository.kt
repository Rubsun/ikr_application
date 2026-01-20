package com.ikr.features.tasks.impl.data.repository

import com.ikr.features.tasks.impl.data.dto.TaskDto
import com.ikr.libs.network.ApiClient
import com.ikr.libs.network.ApiRequest
import com.ikr.libs.network.ApiResponse
import com.ikr.libs.network.HttpMethod
import com.ikr.libs.storage.StorageProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

/**
 * Repository для работы с задачами
 */
internal class TasksRepository(
    private val storageProvider: StorageProvider,
    private val apiClient: ApiClient
) {
    private val tasksKey = "tasks_list"
    private val _tasks = MutableStateFlow<List<TaskDto>>(emptyList())

    init {
        loadTasksFromStorage()
    }

    /**
     * Получить все задачи
     */
    fun getTasks(): Flow<List<TaskDto>> = _tasks.asStateFlow()

    /**
     * Получить задачу по ID
     */
    suspend fun getTaskById(id: String): TaskDto? {
        return withContext(Dispatchers.IO) {
            _tasks.value.find { it.id == id }
        }
    }

    /**
     * Добавить задачу
     */
    suspend fun addTask(title: String, description: String) {
        withContext(Dispatchers.IO) {
            val newTask = TaskDto(
                id = System.currentTimeMillis().toString(),
                title = title,
                description = description,
                completed = false,
                createdAt = System.currentTimeMillis()
            )
            val updatedTasks = _tasks.value + newTask
            _tasks.value = updatedTasks
            saveTasksToStorage(updatedTasks)
        }
    }

    /**
     * Обновить задачу
     */
    suspend fun updateTask(task: TaskDto) {
        withContext(Dispatchers.IO) {
            val updatedTasks = _tasks.value.map {
                if (it.id == task.id) task else it
            }
            _tasks.value = updatedTasks
            saveTasksToStorage(updatedTasks)
        }
    }

    /**
     * Удалить задачу
     */
    suspend fun deleteTask(id: String) {
        withContext(Dispatchers.IO) {
            val updatedTasks = _tasks.value.filter { it.id != id }
            _tasks.value = updatedTasks
            saveTasksToStorage(updatedTasks)
        }
    }

    /**
     * Загрузить задачи из внешнего API
     */
    suspend fun loadTasksFromApi(): Flow<List<TaskDto>> = flow {
        withContext(Dispatchers.IO) {
            delay(1000) // Имитация сетевого запроса
            
            // Пример использования API клиента
            // В реальном проекте здесь будет реальный запрос
            val fakeApiTasks = generateFakeTasks()
            
            val updatedTasks = _tasks.value + fakeApiTasks
            _tasks.value = updatedTasks
            saveTasksToStorage(updatedTasks)
            
            emit(updatedTasks)
        }
    }

    /**
     * Генерация фейковых задач для демонстрации
     */
    private fun generateFakeTasks(): List<TaskDto> {
        return listOf(
            TaskDto(
                id = "fake_${System.currentTimeMillis()}_1",
                title = "Задача из API",
                description = "Эта задача загружена из внешнего API",
                completed = false,
                imageUrl = "https://picsum.photos/200/200?random=1",
                createdAt = System.currentTimeMillis()
            ),
            TaskDto(
                id = "fake_${System.currentTimeMillis()}_2",
                title = "Еще одна задача",
                description = "Описание задачи из API",
                completed = true,
                imageUrl = "https://picsum.photos/200/200?random=2",
                createdAt = System.currentTimeMillis()
            )
        )
    }

    /**
     * Сохранить задачи в хранилище
     */
    private suspend fun saveTasksToStorage(tasks: List<TaskDto>) {
        val json = Json { ignoreUnknownKeys = true }
        val jsonString = json.encodeToString(
            kotlinx.serialization.builtins.ListSerializer(TaskDto.serializer()),
            tasks
        )
        storageProvider.saveString(tasksKey, jsonString)
    }

    /**
     * Загрузить задачи из хранилища
     */
    private suspend fun loadTasksFromStorage() {
        val json = Json { ignoreUnknownKeys = true }
        val jsonString = storageProvider.getString(tasksKey)
        
        if (jsonString.isNotEmpty()) {
            try {
                val tasks = json.decodeFromString(
                    kotlinx.serialization.builtins.ListSerializer(TaskDto.serializer()),
                    jsonString
                )
                _tasks.value = tasks
            } catch (e: Exception) {
                // Если не удалось распарсить, используем пустой список
                _tasks.value = emptyList()
            }
        } else {
            // Если нет сохраненных данных, создаем начальные фейковые задачи
            _tasks.value = listOf(
                TaskDto(
                    id = "1",
                    title = "Изучить Kotlin Flow",
                    description = "Изучить работу с Flow и StateFlow",
                    completed = false,
                    createdAt = System.currentTimeMillis()
                ),
                TaskDto(
                    id = "2",
                    title = "Создать фича-модуль",
                    description = "Реализовать модуль с правильной архитектурой",
                    completed = true,
                    createdAt = System.currentTimeMillis() - 86400000
                ),
                TaskDto(
                    id = "3",
                    title = "Настроить DI",
                    description = "Настроить dependency injection с Koin",
                    completed = false,
                    createdAt = System.currentTimeMillis() - 172800000
                )
            )
            saveTasksToStorage(_tasks.value)
        }
    }
}

