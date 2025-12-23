package com.example.data.mapper

import com.example.data.db.CatEntity
import com.example.api.CatDto

fun CatEntity.toDto() : CatDto {
    return CatDto(
        name = this.name,
        phrase = this.phrase,
        imageUrl = this.imageUrl,
        fetchedFrom = this.fetchedFrom
    )
}

fun List<CatEntity>.toDtoList() : List<CatDto> {
    return this.map { it.toDto() }
}