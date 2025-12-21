package com.rin2396.impl.data

import com.rin2396.impl.data.models.RinInfo

internal interface RinStorage {
    fun save(entry: RinInfo)
    fun getAll(): List<RinInfo>
}
