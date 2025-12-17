package com.example.ikr_application.MomusWinner.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ikr_application.MomusWinner.data.models.Point
import com.example.ikr_application.MomusWinner.data.models.PointsState
import com.example.ikr_application.MomusWinner.domain.AddPointUseCaseImpl
import com.example.pointsgraph.data.repository.PointsRepositoryImpl
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PointsViewModel : ViewModel() {

    private val repository = PointsRepositoryImpl()
    private val addPointUseCase = AddPointUseCaseImpl(repository)

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