package com.tire.storage.data

import android.content.Context
import androidx.room.Room
import com.example.injector.AbstractInitializer
import com.tire.storage.api.PokemonLocalDataSource
import com.tire.storage.data.dao.PokemonDao
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single {
                    Room.databaseBuilder(
                        context,
                        PokeBase::class.java,
                        PokeBase.DATABASE_NAME
                    ).fallbackToDestructiveMigration(true)
                        .build()
                }
                single<PokemonDao> { get<PokeBase>().pokemonDao() }
                single<PokemonLocalDataSource> { PokemonLocalDataSourceImpl(get()) }
            }
        )
    }
}
