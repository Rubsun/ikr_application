package com.tire.api.domain.models

data class CasePreview(
    val caseName: String,
    val caseInfo: String,
    val caseImageUrl: String?,
    val pokemons: List<Pokemon>
)

