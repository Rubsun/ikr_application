package com.egorik4.impl.data.storage

import com.egorik4.api.domain.models.Book
import com.example.primitivestorage.api.PrimitiveStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable

@Serializable
internal data class CachedBooks(
    val lastQuery: String = "",
    val books: List<CachedBook> = emptyList()
)

@Serializable
internal data class CachedBook(
    val title: String,
    val author: String,
    val year: Int?,
    val coverImageUrl: String?
)

internal class Egorik4Storage(
    private val storage: PrimitiveStorage<CachedBooks>
) {
    fun getLastQuery(): Flow<String> {
        return storage.get().map { it?.lastQuery ?: "" }
    }

    suspend fun saveLastQuery(query: String) {
        storage.patch { cached ->
            (cached ?: CachedBooks()).copy(lastQuery = query)
        }
    }

    fun getCachedBooks(): Flow<List<Book>> {
        return storage.get().map { cached ->
            cached?.books?.map { it.toBook() } ?: emptyList()
        }
    }

    suspend fun saveBooks(books: List<Book>) {
        storage.patch { cached ->
            (cached ?: CachedBooks()).copy(
                books = books.map { it.toCached() }
            )
        }
    }

    private fun CachedBook.toBook(): Book = Book(
        title = title,
        author = author,
        year = year,
        coverImageUrl = coverImageUrl
    )

    private fun Book.toCached(): CachedBook = CachedBook(
        title = title,
        author = author,
        year = year,
        coverImageUrl = coverImageUrl
    )
}
