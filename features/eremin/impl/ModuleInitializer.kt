package com.example.ikr_application.features.eremin.impl

import android.content.Context
import com.example.injector.AbstractInitializer
import org.koin.core.context.loadKoinModules

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(ereminModule)
    }
}
