package com.michaelnoskov.impl

import android.content.Context
import com.example.injector.AbstractInitializer
import com.example.libs.arch.ScreenFragmentRouter
import com.michaelnoskov.api.Constants
import com.michaelnoskov.api.domain.repository.ColorSquareRepository
import com.michaelnoskov.impl.data.datasource.LocalDataSource
import com.michaelnoskov.impl.data.datasource.LocalDataSourceImpl
import com.michaelnoskov.impl.data.datasource.RemoteDataSource
import com.michaelnoskov.impl.data.datasource.RemoteDataSourceImpl
import com.michaelnoskov.impl.data.mapper.DataMapper
import com.michaelnoskov.impl.data.repository.ColorSquareRepositoryImpl
import com.michaelnoskov.impl.data.storage.DefaultPrimitiveStorageFactory
import com.michaelnoskov.impl.ui.fragment.ColorSquareFragment
import com.michaelnoskov.impl.ui.viewmodel.ColorSquareViewModel
import com.michaelnoskov.network.api.ColorSquareApiClient
import com.michaelnoskov.network.api.WeatherApiClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                // Storage
                single { DefaultPrimitiveStorageFactory(context) }
                
                // Data sources
                single<LocalDataSource> { LocalDataSourceImpl(context, get(), DataMapper()) }
                single<RemoteDataSource> { 
                    RemoteDataSourceImpl(
                        get<ColorSquareApiClient>(),
                        get<WeatherApiClient>()
                    ) 
                }
                
                // Repository
                single<ColorSquareRepository> { ColorSquareRepositoryImpl(get(), get()) }
                
                // ViewModel
                viewModel { ColorSquareViewModel(androidContext() as android.app.Application, get()) }
                
                // Fragment
                intoSetFactory(Constants.MICHAELNOSKOV_SCREEN) {
                    ScreenFragmentRouter(R.string.michaelnoskov_title, ColorSquareFragment::class)
                }
            }
        )
    }
}

