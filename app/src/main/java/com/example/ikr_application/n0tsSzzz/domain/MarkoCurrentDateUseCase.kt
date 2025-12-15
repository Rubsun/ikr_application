package com.example.ikr_application.n0tsSzzz.domain

import android.annotation.SuppressLint
import com.example.ikr_application.n0tsSzzz.data.MarkoRepository
import java.util.Date

class MarkoCurrentDateUseCase() {
    @SuppressLint("DiscouragedApi")
    fun date(): Date {
        val timestamp = MarkoRepository.INSTANCE.deviceInfo().currentTime
        val date = Date(timestamp)

        return date
    }
}

