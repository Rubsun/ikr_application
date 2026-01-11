package com.drain678.impl

import android.content.Context
import com.drain678.api.Constants
import com.drain678.api.domain.usecases.AddTimeRecordUseCase
import com.drain678.api.domain.usecases.Drain678CurrentDateUseCase
import com.drain678.api.domain.usecases.Drain678ElapsedTimeUseCase
import com.drain678.api.domain.usecases.FilterTimeRecordsUseCase
import com.drain678.impl.data.Drain678Repository
import com.drain678.impl.domain.AddTimeRecordUseCaseImpl
import com.drain678.impl.domain.Drain678CurrentDateUseCaseImpl
import com.drain678.impl.domain.Drain678ElapsedTimeUseCaseImpl
import com.drain678.impl.domain.FilterTimeRecordsUseCaseImpl
import com.drain678.impl.ui.Drain678Fragment
import com.drain678.impl.ui.Drain678ViewModel
import com.drain678.network.api.TimeApiClient
import com.example.injector.AbstractInitializer
import com.example.libs.arch.ScreenFragmentRouter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

/**
 * Модуль для конфигурации зависимостей
 * Его надо добавлять в манифест, что бы KOIN + startup их собрали
 */
internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single { Drain678Repository(context, get<TimeApiClient>()) }


                factory<Drain678CurrentDateUseCase> { Drain678CurrentDateUseCaseImpl(get()) }
                factory<Drain678ElapsedTimeUseCase> { Drain678ElapsedTimeUseCaseImpl(get()) }
                factory<FilterTimeRecordsUseCase> { FilterTimeRecordsUseCaseImpl(get()) }
                factory<AddTimeRecordUseCase> { AddTimeRecordUseCaseImpl(get()) }

                viewModel { Drain678ViewModel() }

                intoSetFactory(Constants.DRAIN678_SCREEN) {
                    ScreenFragmentRouter(R.string.drain678_title, Drain678Fragment::class)
                }
            }
        )
    }
}

