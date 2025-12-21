package com.momuswinner.api.domain.models

sealed interface PointsState {
    data object Loading : PointsState
    data object Empty : PointsState
    data class Success(val points: List<Point>) : PointsState
}