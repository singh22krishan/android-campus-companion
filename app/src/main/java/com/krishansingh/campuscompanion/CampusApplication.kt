package com.krishansingh.campuscompanion

import android.app.Application
import com.krishansingh.campuscompanion.data.BookRepository
import com.krishansingh.campuscompanion.data.CampusDatabase

class CampusApplication : Application() {
    val bookRepository by lazy {
        BookRepository(CampusDatabase.get(this).bookDao())
    }
}
