package com.akiko23.impl.data.models

import com.akiko23.api.domain.models.TimePrecision
import kotlinx.serialization.Serializable

/**
 * Модель состояния для сохранения в персистентное хранилище.
 */
@Serializable
internal data class Akiko23State(
    val selectedPrecision: String = "S",
    val showCat: Boolean = false,
) {
    fun toPrecision(): TimePrecision {
        return TimePrecision.entries.find { it.typeName == selectedPrecision }
            ?: TimePrecision.S
    }

    companion object {
        fun fromPrecision(precision: TimePrecision, showCat: Boolean): Akiko23State {
            return Akiko23State(
                selectedPrecision = precision.typeName,
                showCat = showCat,
            )
        }
    }
}

