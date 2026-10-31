package com.krishansingh.campuscompanion.features.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.krishansingh.campuscompanion.data.Book
import com.krishansingh.campuscompanion.data.BookRepository
import com.krishansingh.campuscompanion.validation.Validators
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookViewModel(private val repository: BookRepository) : ViewModel() {
    val books: StateFlow<List<Book>> = repository.books
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun add(code: String, name: String, author: String, available: Boolean): Boolean {
        if (!Validators.validBook(code, name, author)) return false
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(
                Book(
                    code = code.trim(),
                    name = name.trim(),
                    author = author.trim(),
                    available = available
                )
            )
        }
        return true
    }

    fun delete(book: Book) {
        viewModelScope.launch(Dispatchers.IO) { repository.delete(book) }
    }
}

class BookViewModelFactory(private val repository: BookRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
            return BookViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
