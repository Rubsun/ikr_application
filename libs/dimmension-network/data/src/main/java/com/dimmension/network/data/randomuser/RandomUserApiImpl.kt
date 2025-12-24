package com.dimmension.network.data.randomuser

import com.dimmension.network.api.RandomUserApi
import com.dimmension.network.api.RandomUserData
import com.dimmension.network.data.RetrofitNetworkClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val BASE_URL = "https://randomuser.me/"

internal class RandomUserApiImpl(
    private val networkClient: RetrofitNetworkClient,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RandomUserApi {

    private val apiService: RandomUserApiService by lazy {
        networkClient.createService(BASE_URL, RandomUserApiService::class)
    }

    override suspend fun getRandomUsers(count: Int): Result<List<RandomUserData>> = 
        withContext(ioDispatcher) {
            runCatching {
                val response = apiService.getRandomUsers(count = count)
                response.results.map { user ->
                    RandomUserData(
                        firstName = user.name.first,
                        lastName = user.name.last
                    )
                }
            }
        }
}

