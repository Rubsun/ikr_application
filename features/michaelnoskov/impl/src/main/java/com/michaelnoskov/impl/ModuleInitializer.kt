package com.michaelnoskov.impl

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.injector.AbstractInitializer
import com.michaelnoskov.api.Constants
import com.michaelnoskov.api.domain.repository.ColorSquareRepository
import com.michaelnoskov.impl.data.api.MockColorSquareApi
import com.michaelnoskov.impl.data.datasource.LocalDataSourceImpl
import com.michaelnoskov.impl.data.datasource.RemoteDataSourceImpl
import com.michaelnoskov.impl.data.mapper.DataMapper
import com.michaelnoskov.impl.data.mapper.NetworkMapper
import com.michaelnoskov.impl.data.repository.ColorSquareRepositoryImpl
import com.michaelnoskov.impl.data.storage.DefaultPrimitiveStorageFactory
import com.michaelnoskov.impl.ui.fragment.ColorSquareFragment
import com.michaelnoskov.impl.ui.viewmodel.ColorSquareViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                // Storage
                single { DefaultPrimitiveStorageFactory(context) }
                
                // Data sources
                single { LocalDataSourceImpl(context, get(), DataMapper()) }
                single { RemoteDataSourceImpl(MockColorSquareApi(), NetworkMapper()) }
                
                // Repository
                single<ColorSquareRepository> { ColorSquareRepositoryImpl(get(), get()) }
                
                // ViewModel
                viewModel { ColorSquareViewModel(context.applicationContext as android.app.Application) }
                
                // Fragment
                factory<Class<out Fragment>>(named(Constants.MICHAELNOSKOV_SCREEN)) { 
                    ColorSquareFragment::class.java 
                }
            }
        )
    }
}

