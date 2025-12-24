package com.dimmension.network.api

import kotlin.reflect.KClass

/**
 * Интерфейс для выполнения сетевых запросов.
 * Абстрагирует конкретную реализацию (Retrofit, Ktor, OkHttp и т.д.)
 */
interface DimmensionNetworkClient {
    /**
     * Создает экземпляр API сервиса
     * @param baseUrl базовый URL для запросов
     * @param serviceClass класс API интерфейса
     * @return экземпляр API сервиса
     */
    fun <T : Any> createService(baseUrl: String, serviceClass: KClass<T>): T
}

/**
 * Inline extension для удобного создания сервисов
 */
inline fun <reified T : Any> DimmensionNetworkClient.createService(baseUrl: String): T {
    return createService(baseUrl, T::class)
}

