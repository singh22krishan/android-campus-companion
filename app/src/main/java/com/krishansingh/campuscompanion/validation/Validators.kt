package com.krishansingh.campuscompanion.validation

object Validators {
    fun validBook(code: String, name: String, author: String): Boolean =
        code.isNotBlank() && name.isNotBlank() && author.isNotBlank()

    fun validTask(title: String, dueDate: String): Boolean =
        title.isNotBlank() && dueDate.isNotBlank()

    fun validStudent(name: String, phone: String, email: String): Boolean =
        name.isNotBlank() &&
            phone.isNotBlank() &&
            email.contains("@") &&
            email.substringAfter("@").contains(".")
}
