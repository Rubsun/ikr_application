package com.ikr.features.tasks.impl.di

import com.ikr.features.tasks.api.TasksFeatureProvider
import com.ikr.features.tasks.impl.data.repository.TasksRepository
import com.ikr.features.tasks.impl.domain.usecase.AddTaskUseCase
import com.ikr.features.tasks.impl.domain.usecase.FilterTasksUseCase
import com.ikr.features.tasks.impl.domain.usecase.GetTasksUseCase
import com.ikr.features.tasks.impl.domain.usecase.LoadTasksFromApiUseCase
import com.ikr.features.tasks.impl.domain.usecase.ToggleTaskCompletionUseCase
import com.ikr.features.tasks.impl.ui.fragment.TasksFragment
import com.ikr.features.tasks.impl.ui.viewmodel.TasksViewModel
import com.ikr.libs.network.ApiClient
import com.ikr.libs.storage.StorageProvider
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin модуль для фича-модуля задач
 */
internal val tasksModule = module {
    // Repository
    single<TasksRepository> {
        TasksRepository(
            storageProvider = get<StorageProvider>(),
            apiClient = get<ApiClient>()
        )
    }

    // UseCases
    factory<GetTasksUseCase> {
        GetTasksUseCase(repository = get())
    }

    factory<FilterTasksUseCase> {
        FilterTasksUseCase()
    }

    factory<AddTaskUseCase> {
        AddTaskUseCase(repository = get())
    }

    factory<ToggleTaskCompletionUseCase> {
        ToggleTaskCompletionUseCase(repository = get())
    }

    factory<LoadTasksFromApiUseCase> {
        LoadTasksFromApiUseCase(repository = get())
    }

    // ViewModel
    viewModel<TasksViewModel> {
        TasksViewModel(
            getTasksUseCase = get(),
            filterTasksUseCase = get(),
            addTaskUseCase = get(),
            toggleTaskCompletionUseCase = get(),
            loadTasksFromApiUseCase = get()
        )
    }

    // Feature Provider
    single<TasksFeatureProvider> {
        object : TasksFeatureProvider {
            override fun getTasksFragment() = TasksFragment()
        }
    }
}

