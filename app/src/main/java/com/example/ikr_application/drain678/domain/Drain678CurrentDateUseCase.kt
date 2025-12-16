package com.example.ikr_application.drain678.domain

import com.example.ikr_application.drain678.data.Drain678Repository
import java.util.Date

class Drain678CurrentDateUseCase() {
    fun date(): Date {
        val timestamp = Drain678Repository.INSTANCE.drain678Info().currentTime
        val date = Date(timestamp)

        return date
    }
}
