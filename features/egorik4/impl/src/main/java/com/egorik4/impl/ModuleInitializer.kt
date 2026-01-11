package com.egorik4.impl

import android.content.Context
import com.egorik4.api.Constants
import com.egorik4.api.domain.usecases.SearchBooksUseCase
import com.egorik4.impl.data.BooksRepository
import com.egorik4.impl.data.storage.CachedBooks
import com.egorik4.impl.data.storage.Egorik4Storage
import com.egorik4.impl.domain.SearchBooksUseCaseImpl
import com.egorik4.impl.ui.Egorik4Fragment
import com.egorik4.impl.ui.Egorik4ViewModel
import com.egorik4.network.api.BooksApiClient
import com.example.injector.AbstractInitializer
import com.example.libs.arch.ScreenFragmentRouter
import com.example.primitivestorage.api.PrimitiveStorage
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

private const val STORAGE_NAME = "egorik4_storage"

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                // BooksApiClient регистрируется в libs/egorik4-network/data модуле
                single {
                    BooksRepository(get<BooksApiClient>())
                }

                single<PrimitiveStorage<CachedBooks>> {
                    val factory: PrimitiveStorage.Factory = get()
                    factory.create(
                        name = STORAGE_NAME,
                        serializer = CachedBooks.serializer()
                    )
                }

                single {
                    Egorik4Storage(get())
                }

                factory<SearchBooksUseCase> {
                    SearchBooksUseCaseImpl(get())
                }

                viewModel {
                    Egorik4ViewModel(get())
                }

                intoSetFactory(Constants.EGORIK4_SCREEN) {
                    ScreenFragmentRouter(R.string.title_egorik4_screen, Egorik4Fragment::class)
                }
            }
        )
    }
}

