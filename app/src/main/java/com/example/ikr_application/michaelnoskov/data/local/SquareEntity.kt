package com.example.ikr_application.michaelnoskov.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "square_data")
data class SquareEntity(
    @PrimaryKey val id: String = "default",
    val color: Int,
    val size: Int,
    val rotation: Float,
    val alpha: Float,
    val updatedAt: Long
)