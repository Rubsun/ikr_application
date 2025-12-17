package com.example.ikr_application.MomusWinner.data.models
sealed interface PointsState {
    data object Loading : PointsState
    data object Empty : PointsState
    data class Success(val points: List<Point>) : PointsState
}