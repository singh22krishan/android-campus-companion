package com.krishansingh.campuscompanion

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidationTest {
    @Test
    fun book_requires_code_name_and_author() {
        assertTrue(Validators.validBook("BK-101", "Clean Code", "Robert Martin"))
        assertFalse(Validators.validBook("", "Clean Code", "Robert Martin"))
    }

    @Test
    fun task_requires_title_and_due_date() {
        assertTrue(Validators.validTask("Prepare test cases", "2026-11-10"))
        assertFalse(Validators.validTask("", "2026-11-10"))
    }

    @Test
    fun student_requires_basic_email_shape() {
        assertTrue(Validators.validStudent("Alex", "5551234567", "alex@example.com"))
        assertFalse(Validators.validStudent("Alex", "5551234567", "alex.example.com"))
    }
}
