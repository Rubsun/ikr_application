package com.example.logger.timber

import android.content.Context
import com.example.injector.AbstractInitializer
import com.example.logger.api.Logger
import timber.log.Timber

internal class ModuleInitializer : AbstractInitializer<Logger>() {
    override fun create(context: Context): Logger {
        Logger.setEngine(timberEngine())

        return Logger
    }

    private fun timberEngine(): Logger.Engine {
        Timber.plant(Timber.DebugTree())

        return object : Logger.Engine {
            override fun debug(message: String) = Timber.d(message)
        }
    }
}