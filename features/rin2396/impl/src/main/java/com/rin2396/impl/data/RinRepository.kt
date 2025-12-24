package com.rin2396.impl.data

import com.rin2396.impl.data.models.RinInfo
import com.rin2396.impl.data.RinStorage

internal class RinRepository(
    private val storage: RinStorage
) {
    fun addEntry(entry: RinInfo) = storage.save(entry)
    fun getEntries(): List<RinInfo> = storage.getAll()
}
