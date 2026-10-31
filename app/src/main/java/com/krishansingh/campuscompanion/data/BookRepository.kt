package com.krishansingh.campuscompanion.data

import kotlinx.coroutines.flow.Flow

class BookRepository(private val dao: BookDao) {
    val books: Flow<List<Book>> = dao.getAll()
    suspend fun insert(book: Book) = dao.insert(book)
    suspend fun delete(book: Book) = dao.delete(book)
}
