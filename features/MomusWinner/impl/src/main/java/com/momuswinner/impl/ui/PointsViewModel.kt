package com.momuswinner.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.momuswinner.api.domain.AddPointUseCase
import com.momuswinner.api.domain.GetQuoteUseCase
import com.momuswinner.api.domain.models.Point
import com.momuswinner.api.domain.models.PointsState
import com.momuswinner.api.domain.models.QuoteState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class PointsViewModel(private val addPointUseCase: AddPointUseCase,private val getQuoteUseCase: GetQuoteUseCase) : ViewModel() {

    val pointsState: StateFlow<PointsState> = addPointUseCase.getPoints()
        .map { points ->
            if (points.isEmpty()) {
                PointsState.Empty
            } else {
                PointsState.Success(points)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PointsState.Loading
        )

    private val _quoteState = MutableStateFlow<QuoteState>(QuoteState.Idle)
    val quoteState: StateFlow<QuoteState> = _quoteState.asStateFlow()

    fun getQuote() {
        viewModelScope.launch {
            _quoteState.value = QuoteState.Loading
            try {
                val quote = getQuoteUseCase.getQuote()
                _quoteState.value = QuoteState.Success(quote)
            } catch (e: Exception) {
                _quoteState.value = QuoteState.Error(e.message ?: "Unknown error")
            }
        }
    }


    fun addPoint(x: Double, y: Double) {
        viewModelScope.launch {
            addPointUseCase.execute(Point(x, y))
        }
    }

    fun clearPoints() {
        viewModelScope.launch {
            addPointUseCase.clearPoints()
        }
    }
}