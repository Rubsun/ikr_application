package com.egorik4.impl

import android.content.Context
import androidx.fragment.app.Fragment
import com.egorik4.api.Constants
import com.egorik4.api.domain.usecases.SearchBooksUseCase
import com.egorik4.impl.data.BooksRepository
import com.egorik4.impl.data.createBooksService
import com.egorik4.impl.data.storage.CachedBooks
import com.egorik4.impl.data.storage.Egorik4Storage
import com.egorik4.impl.domain.SearchBooksUseCaseImpl
import com.egorik4.impl.ui.Egorik4Fragment
import com.egorik4.impl.ui.Egorik4ViewModel
import com.example.injector.AbstractInitializer
import com.example.primitivestorage.api.PrimitiveStorage
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val BASE_URL = "https://openlibrary.org/"
private const val STORAGE_NAME = "egorik4_storage"

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single {
                    Json { ignoreUnknownKeys = true }
                }

                single {
                    createBooksService(BASE_URL)
                }

                single {
                    BooksRepository(get())
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

                factory<Class<out Fragment>>(named(Constants.EGORIK4_SCREEN)) {
                    Egorik4Fragment::class.java
                }
            }
        )
    }
}

