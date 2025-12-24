package com.roomstorage.data

import android.content.Context
import androidx.room.Room
import com.example.api.CatRoomRepository
import com.roomstorage.data.db.CatDatabase
import com.example.injector.AbstractInitializer
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single {
                    Room.databaseBuilder(
                        androidContext(),
                        CatDatabase::class.java,
                        "cat_database"
                    ).build()
                }

                single {
                    get<CatDatabase>().catDao()
                }

                single<CatRoomRepository> {
                    CatRoomRepositoryImpl(get())
                }
            }
        )
    }

}