package com.grigoran.api.domain

interface AddItemUseCase {
    operator fun invoke(title:String, price:Int)
}