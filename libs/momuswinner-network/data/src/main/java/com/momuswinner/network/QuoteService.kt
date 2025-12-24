package com.momuswinner.network

import com.momuswinner.network.api.models.QuoteDto
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.http.GET
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

internal interface QuoteService {
    @GET("v1/quotes")
    suspend fun getQuotes(): List<QuoteDto>
}

// Кастомный конвертер для List<QuoteDto>
internal class ListConverterFactory(
    private val json: Json
) : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        if (type is ParameterizedType) {
            val rawType = type.rawType
            if (rawType == List::class.java) {
                val actualType = type.actualTypeArguments[0]
                if (actualType.toString().contains("com.momuswinner.network.api.models.QuoteDto")) {
                    return Converter<ResponseBody, List<QuoteDto>> { value ->
                        json.decodeFromString<List<QuoteDto>>(value.string())
                    }
                }
            }
        }
        return null
    }
}