package com.rin2396.impl.data

internal class RinPrefsStorage(
    context: Context
) : RinStorage {

    private val prefs =
        context.getSharedPreferences("rin_storage", Context.MODE_PRIVATE)

    override fun save(entry: RinInfo) {
        prefs.edit()
            .putLong(entry.timestamp.toString(), entry.timestamp)
            .apply()
    }

    override fun getAll(): List<RinInfo> =
        prefs.all.values.map { RinInfo(it as Long) }
}
