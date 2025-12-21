package com.rin2396.impl.domain

import com.rin2396.impl.data.RinRepository
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

internal class RinCurrentDateUseCase {
    fun date(): String = java.time.LocalDate.now().toString()
}
