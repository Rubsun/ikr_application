package com.tire.impl.data.config

import android.content.Context
import com.tire.api.domain.models.PokemonCase
import com.tire.impl.data.config.dto.CasesConfigDto
import com.tire.impl.domain.mappers.CaseMapper
import com.tire.impl.domain.validation.CaseValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

internal class CaseConfigLoader(
    private val context: Context
) {
    companion object {
        private const val CONFIG_FILE = "cases_config.json"
    }

    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    suspend fun loadCases(): List<PokemonCase> = withContext(Dispatchers.IO) {
        try {
            val jsonString = context.assets.open(CONFIG_FILE)
                .bufferedReader()
                .use { it.readText() }
            val configDto = json.decodeFromString<CasesConfigDto>(jsonString)
            val cases = CaseMapper.toDomain(configDto.cases)
            val validCases = cases.filter { case ->
                when (val result = CaseValidator.validate(case)) {
                    is CaseValidator.ValidationResult.Success -> true
                    is CaseValidator.ValidationResult.Error -> {
                        println("Invalid case '${case.id}': ${result.errors}")
                        false
                    }
                }
            }
            validCases

        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun getCaseById(caseId: String): PokemonCase? {
        return loadCases().find { it.id == caseId }
    }
}
