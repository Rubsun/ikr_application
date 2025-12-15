package com.example.ikr_application.nastyazz.ui

class ItemsViewModel(
    observeItemsUseCase: ObserveItemsUseCase,
    private val addItemUseCase: AddItemUseCase
) : ViewModel() {

    private val query = MutableStateFlow("")

    val state: StateFlow<ItemsState> =
        combine(
            observeItemsUseCase(),
            query
        ) { items, q ->
            ItemsState(
                items = items.filter {
                    it.title.contains(q, ignoreCase = true)
                },
                query = q
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ItemsState(isLoading = true)
        )

    fun onSearch(text: String) {
        query.value = text
    }

    fun onAddClicked(title: String) {
        viewModelScope.launch {
            addItemUseCase(title)
        }
    }
}
