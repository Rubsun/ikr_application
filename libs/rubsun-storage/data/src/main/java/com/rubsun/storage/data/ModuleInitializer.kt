package com.rubsun.storage.data

import android.content.Context
import com.example.injector.AbstractInitializer
import com.rubsun.storage.api.NumberStorage
import com.rubsun.storage.data.database.RoomNumberDatabase
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single { RoomNumberDatabase.getDatabase(context) }
                single { get<RoomNumberDatabase>().numberDao() }
                single<NumberStorage> {
                    RoomNumberStorage(get())
                }
            }
        )
    }
}


