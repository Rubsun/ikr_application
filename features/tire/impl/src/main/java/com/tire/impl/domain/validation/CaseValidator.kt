package com.tire.impl.domain.validation

import com.tire.api.domain.models.LootEntry
import com.tire.api.domain.models.PokemonCase

internal object CaseValidator {

    fun validate(case: PokemonCase): ValidationResult {
        val errors = mutableListOf<String>()
        if (case.id.isBlank()) {
            errors.add("Case ID cannot be empty")
        }
        if (case.name.isBlank()) {
            errors.add("Case name cannot be empty")
        }
        if (case.lootTable.isEmpty()) {
            errors.add("Loot table cannot be empty")
        }
        if (!validateDropChances(case.lootTable)) {
            errors.add("Drop chances must sum to 1.0 (got: ${case.lootTable.sumOf { it.dropChance.toDouble() }})")
        }
        case.lootTable.forEachIndexed { index, entry ->
            validateLootEntry(entry, index)?.let { errors.add(it) }
        }
        return if (errors.isEmpty()) {
            ValidationResult.Success
        } else {
            ValidationResult.Error(errors)
        }
    }

    private fun validateDropChances(lootTable: List<LootEntry>): Boolean {
        val sum = lootTable.sumOf { it.dropChance.toDouble() }
        return sum in 0.99..1.01 // допуск на погрешность
    }

    private fun validateLootEntry(entry: LootEntry, index: Int): String? {
        return when {
            entry.pokemonIds.isEmpty() -> "Loot entry #$index: Pokemon IDs list cannot be empty"
            entry.dropChance !in 0f..1f -> "Loot entry #$index: Drop chance must be between 0 and 1 (got: ${entry.dropChance})"
            else -> null
        }
    }

    sealed class ValidationResult {
        object Success : ValidationResult()
        data class Error(val errors: List<String>) : ValidationResult()
    }
}
