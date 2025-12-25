package com.rin2396.impl

import android.content.Context
import android.util.Log
import com.example.injector.AbstractInitializer
import com.rin2396.api.Constants
import com.rin2396.impl.api.RinFragmentProvider
import com.rin2396.impl.api.RinFragmentProviderImpl
import com.rin2396.impl.di.loadRin2396Module
import com.rin2396.network.data.loadRin2396NetworkModule
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
import androidx.fragment.app.Fragment
import com.rin2396.impl.ui.CatsFragment

class RinModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        try {
            loadRin2396NetworkModule()
        } catch (e: Throwable) {
            Log.e("RinModuleInitializer", "Failed to load network module", e)
        }

        try {
            loadRin2396Module()
        } catch (e: Throwable) {
            Log.e("RinModuleInitializer", "Failed to load feature module", e)
        }

        // Регистрация provider в DI — тоже в try/catch
        try {
            loadKoinModules(
                module {
                    single<RinFragmentProvider> { RinFragmentProviderImpl() }

                    // register Fragment class under named qualifier so MainActivity can resolve it
                    single<Class<out Fragment>>(named(Constants.SCREEN)) { CatsFragment::class.java }
                }
            )
        } catch (e: Throwable) {
            Log.e("RinModuleInitializer", "Failed to register RinFragmentProvider", e)
        }
    }
}
