package com.eremin.impl

import com.eremin.impl.data.CapybaraRepository
import com.eremin.impl.domain.GetCapybarasUseCase
import org.koin.dsl.module

internal val ereminModule = module {
    factory { CapybaraRepository() }
    factory { GetCapybarasUseCase(get()) }
}
