package com.nastyazz.impel.nastyazz.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nastyazz.impel.nastyazz.data.FakeItemRepository
import com.nastyazz.impel.nastyazz.domain.AddItemUseCase
import com.nastyazz.impel.nastyazz.domain.ObserveItemsUseCase

class ItemsViewModelFactory : ViewModelProvider.Factory {

    private val repository = FakeItemRepository()

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemsViewModel::class.java)) {
            return ItemsViewModel(
                observeItemsUseCase = ObserveItemsUseCase(repository),
                addItemUseCase = AddItemUseCase(repository)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
