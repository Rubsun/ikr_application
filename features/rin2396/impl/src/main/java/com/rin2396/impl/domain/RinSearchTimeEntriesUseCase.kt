package com.rin2396.impl.domain

import com.rin2396.impl.data.RinRepository
import com.rin2396.impl.data.models.RinInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class RinSearchTimeEntriesUseCase(
    private val repository: RinRepository
) {
    fun search(query: String): List<RinInfo> =
        repository.getEntries()
}
