package com.example.ikr_application.grigoran.data

import kotlin.random.Random

data class ItemDto(var id: Int, var title: String, val price: Int)

class Repository {
    fun fetchItem(): List<ItemDto> {
        val len = Random.nextInt(5, 15)

        return List(len) { index ->
            ItemDto(
                id = index + 1,
                title = "title ${index + 1}",
                price = Random.nextInt(1000, 500000)
            )
        }
    }
}