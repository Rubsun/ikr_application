package com.rin2396.impl

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.injector.AbstractInitializer
import com.rin2396.api.Constants
import com.rin2396.impl.data.RinRepository
import com.rin2396.impl.data.RinStorage
import com.rin2396.impl.data.RinPrefsStorage
import com.rin2396.impl.domain.RinAddTimeEntryUseCase
import com.rin2396.impl.domain.RinCurrentDateUseCase
import com.rin2396.impl.domain.RinElapsedTimeUseCase
import com.rin2396.impl.domain.RinSearchTimeEntriesUseCase
import com.rin2396.impl.ui.RinFragment
import com.rin2396.impl.ui.RinViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal class RinInitializer : AbstractInitializer<Unit>() {

    override fun create(context: Context) {
        loadKoinModules(
            module {

                single<RinStorage> { RinPrefsStorage(context) }

                single { RinRepository(get()) }

                factory { RinAddTimeEntryUseCase(get()) }
                factory { RinSearchTimeEntriesUseCase(get()) }
                factory { RinElapsedTimeUseCase() }
                factory { RinCurrentDateUseCase() }

                viewModel {
                    RinViewModel(
                        get(), // addEntry
                        get(), // searchEntries
                        get(), // elapsedTime
                        get()  // currentDate
                    )
                }

                factory<Class<out Fragment>>(named(Constants.SCREEN)) {
                    RinFragment::class.java
                }
            }
        )
    }
}
