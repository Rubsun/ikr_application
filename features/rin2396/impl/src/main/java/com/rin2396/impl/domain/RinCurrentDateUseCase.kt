package com.rin2396.impl.domain

internal class RinCurrentDateUseCase {
    fun date(): String = java.time.LocalDate.now().toString()
}
