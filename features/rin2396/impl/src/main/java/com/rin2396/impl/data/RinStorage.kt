package com.rin2396.impl.data

internal interface RinStorage {
    fun save(entry: RinInfo)
    fun getAll(): List<RinInfo>
}
