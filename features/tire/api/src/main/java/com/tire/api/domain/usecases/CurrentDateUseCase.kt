package com.tire.api.domain.usecases

import java.util.Date

interface CurrentDateUseCase{
    suspend operator fun invoke(): Date
}
