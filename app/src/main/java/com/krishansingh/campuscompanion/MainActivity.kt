package com.krishansingh.campuscompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import com.krishansingh.campuscompanion.features.library.BookViewModel
import com.krishansingh.campuscompanion.features.library.BookViewModelFactory
import com.krishansingh.campuscompanion.features.students.StudentViewModel
import com.krishansingh.campuscompanion.features.tasks.TaskViewModel
import com.krishansingh.campuscompanion.ui.CampusCompanionApp

class MainActivity : ComponentActivity() {
    private val bookViewModel: BookViewModel by viewModels {
        BookViewModelFactory((application as CampusApplication).bookRepository)
    }
    private val taskViewModel: TaskViewModel by viewModels()
    private val studentViewModel: StudentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                CampusCompanionApp(bookViewModel, taskViewModel, studentViewModel)
            }
        }
    }
}
