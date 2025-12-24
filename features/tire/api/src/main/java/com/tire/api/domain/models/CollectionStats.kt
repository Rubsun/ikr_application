package com.tire.api.domain.models

data class CollectionStats(
    val totalOwned: Int,
    val totalPossible: Int,
    val commonOwned: Int,
    val rareOwned: Int,
    val epicOwned: Int,
    val legendaryOwned: Int,
    val completionPercentage: Float,
)