package com.ikr.features.tasks.impl.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikr.features.tasks.impl.domain.usecase.AddTaskUseCase
import com.ikr.features.tasks.impl.domain.usecase.FilterTasksUseCase
import com.ikr.features.tasks.impl.domain.usecase.GetTasksUseCase
import com.ikr.features.tasks.impl.domain.usecase.LoadTasksFromApiUseCase
import com.ikr.features.tasks.impl.domain.usecase.ToggleTaskCompletionUseCase
import com.ikr.features.tasks.impl.ui.state.TasksUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

/**
 * ViewModel для экрана задач
 */
internal class TasksViewModel(
    private val getTasksUseCase: GetTasksUseCase,
    private val filterTasksUseCase: FilterTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val toggleTaskCompletionUseCase: ToggleTaskCompletionUseCase,
    private val loadTasksFromApiUseCase: LoadTasksFromApiUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val tasksFlow = getTasksUseCase.execute()
    
    private val filteredTasksFlow = filterTasksUseCase.execute(tasksFlow, _searchQuery)

    val uiState: StateFlow<TasksUiState> = combine(
        filteredTasksFlow,
        _searchQuery,
        _isLoading,
        _error
    ) { tasks, query, loading, err ->
        TasksUiState(
            tasks = tasks,
            searchQuery = query,
            isLoading = loading,
            error = err
        )
    }.asStateFlow()

    /**
     * Обновить поисковый запрос
     */
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    /**
     * Добавить новую задачу
     */
    fun addTask(title: String, description: String) {
        if (title.isBlank()) {
            _error.value = "Название задачи не может быть пустым"
            return
        }

        viewModelScope.launch {
            try {
                _error.value = null
                addTaskUseCase.execute(title, description)
            } catch (e: Exception) {
                _error.value = "Ошибка при добавлении задачи: ${e.message}"
            }
        }
    }

    /**
     * Переключить статус выполнения задачи
     */
    fun toggleTaskCompletion(taskId: String) {
        viewModelScope.launch {
            try {
                _error.value = null
                toggleTaskCompletionUseCase.execute(taskId)
            } catch (e: Exception) {
                _error.value = "Ошибка при обновлении задачи: ${e.message}"
            }
        }
    }

    /**
     * Загрузить задачи из API
     */
    fun loadTasksFromApi() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                loadTasksFromApiUseCase.execute().collect {
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _error.value = "Ошибка при загрузке задач: ${e.message}"
            }
        }
    }

    /**
     * Очистить ошибку
     */
    fun clearError() {
        _error.value = null
    }
}

