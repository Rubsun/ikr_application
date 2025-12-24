package com.tire.api.domain.models

data class CaseOpenResult(
    val pokemon: Pokemon,
    val isNew: Boolean,
    val duplicateCount: Int = 0,
)