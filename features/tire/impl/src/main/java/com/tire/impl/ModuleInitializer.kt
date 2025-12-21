package com.tire.impl

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.injector.AbstractInitializer
import com.tire.api.Constants
import com.tire.impl.data.DeviceRepository
import com.tire.impl.data.storage.DeviceStorage
import com.tire.impl.domain.*
import com.tire.impl.ui.DatetimeFragment
import com.tire.impl.ui.DatetimeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single { DeviceStorage(context) }
                single { DeviceRepository(get()) }

                factory { CurrentDateUseCase(get()) }
                factory { ElapsedTimeUseCase(get()) }
                factory { GetDevicesUseCase(get()) }
                factory { AddDeviceUseCase(get()) }

                viewModel { DatetimeViewModel() }

                factory<Class<out Fragment>>(named(Constants.TIRE_SCREEN)) {
                    DatetimeFragment::class.java
                }
            }
        )
    }
}
