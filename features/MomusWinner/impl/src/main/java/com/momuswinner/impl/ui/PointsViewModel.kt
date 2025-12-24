package com.momuswinner.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.momuswinner.api.domain.AddPointUseCase
import com.momuswinner.api.domain.models.Point
import com.momuswinner.api.domain.models.PointsState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class PointsViewModel(private val addPointUseCase: AddPointUseCase) : ViewModel() {

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