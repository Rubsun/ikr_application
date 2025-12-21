package com.example.ikr_application.features.eremin.impl

import com.example.ikr_application.features.eremin.impl.data.CapybaraRepository
import com.example.ikr_application.features.eremin.impl.domain.GetCapybarasUseCase
import org.koin.dsl.module

internal val ereminModule = module {
    factory { CapybaraRepository() }
    factory { GetCapybarasUseCase(get()) }
}
