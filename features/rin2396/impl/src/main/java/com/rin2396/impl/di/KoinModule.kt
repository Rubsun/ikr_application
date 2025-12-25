package com.rin2396.impl.di

import android.content.Context
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.rin2396.api.domain.usecases.AddCatUseCase
import com.rin2396.api.domain.usecases.GetCatsUseCase
import com.rin2396.api.domain.usecases.SearchCatsUseCase
import com.rin2396.impl.data.CatsLocalRepository
import com.rin2396.impl.domain.AddCatUseCaseImpl
import com.rin2396.impl.domain.GetCatsUseCaseImpl
import com.rin2396.impl.domain.SearchCatsUseCaseImpl
import com.rin2396.impl.ui.CatsViewModel
import com.rin2396.impl.ui.ImageLoader
import com.rin2396.network.api.CataasCatApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import java.net.URL

internal class CoilImageLoader : ImageLoader {
    override fun loadImage(url: String, imageView: ImageView) {
        // Simple image loader using coroutines and BitmapFactory to avoid direct Coil dependency
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val stream = URL(url).openStream()
                val bitmap = BitmapFactory.decodeStream(stream)
                withContext(Dispatchers.Main) {
                    imageView.setImageBitmap(bitmap)
                }
                stream.close()
            } catch (e: Exception) {
                // ignore or set placeholder if needed
            }
        }
    }
}

internal fun loadRin2396Module() {
    loadKoinModules(
        module {
            single {
                CatsLocalRepository(androidContext())
            }

            single<GetCatsUseCase> {
                GetCatsUseCaseImpl(get())
            }

            single<AddCatUseCase> {
                AddCatUseCaseImpl(get<CataasCatApiClient>(), get())
            }

            single<SearchCatsUseCase> {
                SearchCatsUseCaseImpl(get())
            }

            single<ImageLoader> {
                CoilImageLoader()
            }

            viewModel {
                CatsViewModel(get(), get(), get())
            }
        }
    )
}
