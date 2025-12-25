package com.argun.impl

import android.content.Context
import androidx.fragment.app.Fragment
import com.argun.api.Constants
import com.argun.api.domain.usecases.AddTimeRecordUseCase
import com.argun.api.domain.usecases.ArgunCurrentDateUseCase
import com.argun.api.domain.usecases.ArgunElapsedTimeUseCase
import com.argun.api.domain.usecases.FilterTimeRecordsUseCase
import com.argun.impl.data.ArgunRepository
import com.argun.impl.domain.AddTimeRecordUseCaseImpl
import com.argun.impl.domain.ArgunCurrentDateUseCaseImpl
import com.argun.impl.domain.ArgunElapsedTimeUseCaseImpl
import com.argun.impl.domain.FilterTimeRecordsUseCaseImpl
import com.argun.impl.ui.ArgunFragment
import com.argun.impl.ui.ArgunViewModel
import com.argun.network.api.TimeApiClient
import com.argun.network.api.TimeFormatter
import com.example.injector.AbstractInitializer
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single { ArgunRepository(context, get<TimeApiClient>()) }
                factory<ArgunCurrentDateUseCase> { ArgunCurrentDateUseCaseImpl(get()) }
                factory<ArgunElapsedTimeUseCase> { ArgunElapsedTimeUseCaseImpl(get()) }
                factory<FilterTimeRecordsUseCase> { FilterTimeRecordsUseCaseImpl(get(), get<TimeFormatter>()) }
                factory<AddTimeRecordUseCase> { AddTimeRecordUseCaseImpl(get()) }
                viewModel { ArgunViewModel() }
                factory<Class<out Fragment>>(named(Constants.ARGUN_SCREEN)) { ArgunFragment::class.java }
            }
        )
    }
}

