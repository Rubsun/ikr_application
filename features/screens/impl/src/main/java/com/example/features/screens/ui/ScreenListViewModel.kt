package com.example.features.screens.ui

import androidx.lifecycle.ViewModel
import com.example.features.screens.domain.Screen
import com.example.features.screens.domain.ScreenListUseCase
import com.example.injector.inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

internal class ScreenListViewModel : ViewModel() {
    private val screenListUseCase: ScreenListUseCase by inject()

    fun state(): Flow<State> {
        return flow {
            val items = screenListUseCase()
            val message = when {
                items.isEmpty() -> "Empty list"
                else -> null
            }

            emit(State(false, items, message))
        }
            .onStart {
                emit(State(true))
            }
            .catch { error ->
                val errorState = State(
                    message = error.message ?: "Unknown error",
                )
                emit(errorState)
            }
    }

    internal data class State(
        val isLoading: Boolean = false,
        val items: List<Screen> = emptyList(),
        val message: String? = null,
    )
}