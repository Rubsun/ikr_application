package com.example.ikr_application.quovadis.data

class CatRepository {
    private val catNames = listOf(
        "Барсик",
        "Мурзик",
        "Рыжик",
        "Felix",
        "Simba",
        "Garfield",
        "Loki",
        "Luna",
        "Boris",
        "Whiskers"
    )

    fun getCat(text: String?): Cat {
        return Cat(name = catNames.random(), phrase = text)
    }
}