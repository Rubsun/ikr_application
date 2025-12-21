package com.rin2396.impl.domain

import android.os.SystemClock
import com.rin2396.impl.data.RinRepository
import com.rin2396.impl.data.models.RinInfo

internal class RinAddTimeEntryUseCase(
    private val repository: RinRepository
) {
    fun execute() {
        repository.addEntry(RinInfo(System.currentTimeMillis()))
    }
}
