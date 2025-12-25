package com.rin2396.impl.domain

import android.util.Log
import com.rin2396.api.domain.usecases.AddCatUseCase
import com.rin2396.impl.data.CatsLocalRepository
import com.rin2396.network.api.CataasCatApiClient
import com.rin2396.api.domain.models.CatModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

private const val TAG = "AddCatUseCase"

internal class AddCatUseCaseImpl(
    private val apiClient: CataasCatApiClient,
    private val repository: CatsLocalRepository
) : AddCatUseCase {
    override fun execute(tag: String): Flow<Unit> = flow {
        try {
            Log.i(TAG, "execute -> requesting cat for tag='$tag'")
            withContext(Dispatchers.IO) {
                val response = apiClient.getRandomCat()
                Log.i(TAG, "execute -> got response id=${response._id} url=${response.url}")
                val rawUrl = response.url
                val imageUrl = if (rawUrl.startsWith("http")) rawUrl else "https://cataas.com${rawUrl}"
                val id = response._id.ifEmpty { imageUrl.hashCode().toString() }
                val cat = CatModel(
                    id = id,
                    imageUrl = imageUrl,
                    tag = tag
                )
                Log.i(TAG, "execute -> saving cat id=${cat.id}")
                repository.addCat(cat)
            }
            emit(Unit)
        } catch (e: Throwable) {
            Log.e(TAG, "execute -> failed", e)
            throw e
        }
    }
}
