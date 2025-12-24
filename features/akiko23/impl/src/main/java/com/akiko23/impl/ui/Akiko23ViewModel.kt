package com.akiko23.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akiko23.impl.data.DeviceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel для экрана akiko23.
 * Показывает случайные волчьи цитаты и картинки.
 */
internal class Akiko23ViewModel(
    private val repository: DeviceRepository,
) : ViewModel() {

    private val quoteFlow = MutableStateFlow<String?>(null)
    private val imageUrlFlow = MutableStateFlow<String?>(null)

    data class State(
        val quoteText: String?,
        val imageUrl: String?,
    )

    fun loadRandomWolfQuote() {
        viewModelScope.launch {
            try {
                val wolfQuote = repository.getRandomWolfQuote()
                quoteFlow.value = wolfQuote.text
                imageUrlFlow.value = wolfQuote.imageUrl
            } catch (e: Exception) {
                e.printStackTrace()
                // Ошибка уже обработана в репозитории, fallback цитата будет возвращена
                // Но на всякий случай показываем сообщение об ошибке
                quoteFlow.value = "Ошибка загрузки цитаты"
                imageUrlFlow.value = null
            }
        }
    }

    private val stateInternal: StateFlow<State> = combine(
        quoteFlow,
        imageUrlFlow,
    ) { quote, imageUrl ->
        State(
            quoteText = quote,
            imageUrl = imageUrl,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = State(
            quoteText = null,
            imageUrl = null,
        ),
    )

    fun state(): StateFlow<State> = stateInternal
}

