package com.example.ikr_application.drain678.data

import android.os.SystemClock
import androidx.annotation.Discouraged
import com.example.ikr_application.drain678.data.model.Drain678Info

class Drain678Repository {
    fun drain678Info(): Drain678Info {
        return Drain678Info(
            currentTime = System.currentTimeMillis(),
            elapsedTime = SystemClock.elapsedRealtime(),
        )
    }

    companion object {
        @Discouraged("Drain678")
        val INSTANCE = Drain678Repository()
    }
}