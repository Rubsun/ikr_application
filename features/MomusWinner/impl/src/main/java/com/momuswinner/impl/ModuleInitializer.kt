package com.momuswinner.impl

import android.content.Context
import com.example.injector.AbstractInitializer
import com.example.libs.arch.ScreenFragmentRouter
import com.momuswinner.api.Constants
import com.momuswinner.api.data.PointsRepository
import com.momuswinner.api.domain.AddPointUseCase
import com.momuswinner.api.domain.GetQuoteUseCase
import com.momuswinner.impl.data.PointsRepositoryImpl
import com.momuswinner.impl.domain.AddPointUseCaseImpl
import com.momuswinner.impl.domain.GetQuoteUseCaseImpl
import com.momuswinner.impl.ui.GraphFragment
import com.momuswinner.impl.ui.PointsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single<PointsRepository> { PointsRepositoryImpl(get()) }
                factory<AddPointUseCase> { AddPointUseCaseImpl(get()) }
                factory<GetQuoteUseCase> { GetQuoteUseCaseImpl(get()) }
                viewModel { PointsViewModel(get(), get()) }
                intoSetFactory(Constants.MOMUS_WINNER_SCREEN) {
                    ScreenFragmentRouter(R.string.areg_title, GraphFragment::class)
                }
            }
        )
    }
}