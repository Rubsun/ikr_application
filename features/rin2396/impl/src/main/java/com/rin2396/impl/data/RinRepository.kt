package com.rin2396.impl.data

import android.os.SystemClock
import androidx.annotation.Discouraged
import com.rin2396.impl.data.models.RinInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

internal class RinRepository(
    private val storage: RinStorage
) {
    fun addEntry(entry: RinInfo) = storage.save(entry)
    fun getEntries(): List<RinInfo> = storage.getAll()
}
