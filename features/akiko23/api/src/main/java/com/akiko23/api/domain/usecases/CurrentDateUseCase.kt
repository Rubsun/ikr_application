package com.akiko23.api.domain.usecases

import java.util.Date

/**
 * UseCase для получения текущей даты.
 */
interface CurrentDateUseCase {
    fun date(): Date
}

