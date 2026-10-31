package com.krishansingh.campuscompanion.features.students

import androidx.lifecycle.ViewModel
import com.krishansingh.campuscompanion.validation.Validators
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class Student(
    val name: String,
    val phone: String,
    val email: String,
    val type: String
)

class StudentViewModel : ViewModel() {
    private val _students = MutableStateFlow<List<Student>>(emptyList())
    val students: StateFlow<List<Student>> = _students

    fun add(student: Student): Boolean {
        if (!Validators.validStudent(student.name, student.phone, student.email)) return false
        _students.update {
            it + student.copy(
                name = student.name.trim(),
                phone = student.phone.trim(),
                email = student.email.trim()
            )
        }
        return true
    }
}
