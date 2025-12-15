interface ItemRepository {

    fun observeItems(): StateFlow<List<ItemDto>>

    suspend fun addItem(item: ItemDto)
}
