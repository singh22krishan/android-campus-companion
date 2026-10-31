package com.krishansingh.campuscompanion.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.krishansingh.campuscompanion.features.library.BookViewModel
import com.krishansingh.campuscompanion.features.students.Student
import com.krishansingh.campuscompanion.features.students.StudentViewModel
import com.krishansingh.campuscompanion.features.tasks.TaskItem
import com.krishansingh.campuscompanion.features.tasks.TaskViewModel

enum class CampusSection(val label: String) {
    LIBRARY("Library"), TASKS("Tasks"), STUDENTS("Students")
}

@Composable
fun CampusCompanionApp(
    bookViewModel: BookViewModel,
    taskViewModel: TaskViewModel,
    studentViewModel: StudentViewModel
) {
    var section by remember { mutableStateOf(CampusSection.LIBRARY) }

    Scaffold(
        topBar = {
            Surface(shadowElevation = 2.dp) {
                Column(Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 14.dp)) {
                    Text("Campus Companion", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Text("Compose · Room · StateFlow", style = MaterialTheme.typography.bodySmall)
                }
            }
        },
        bottomBar = {
            NavigationBar {
                CampusSection.entries.forEach { item ->
                    NavigationBarItem(
                        selected = section == item,
                        onClick = { section = item },
                        icon = { Text(item.label.take(1)) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { padding ->
        Box(Modifier.padding(padding).fillMaxSize()) {
            when (section) {
                CampusSection.LIBRARY -> LibraryScreen(bookViewModel)
                CampusSection.TASKS -> TaskScreen(taskViewModel)
                CampusSection.STUDENTS -> StudentScreen(studentViewModel)
            }
        }
    }
}

@Composable
private fun LibraryScreen(viewModel: BookViewModel) {
    val books by viewModel.books.collectAsState()
    var code by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var available by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Library", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(code, { code = it }, label = { Text("Book code") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(name, { name = it }, label = { Text("Book name") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(author, { author = it }, label = { Text("Author") }, modifier = Modifier.fillMaxWidth())
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(available, { available = it })
                Text("Available")
            }
            error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            Button(
                onClick = {
                    if (viewModel.add(code, name, author, available)) {
                        code = ""; name = ""; author = ""; available = true; error = null
                    } else {
                        error = "Code, name and author are required."
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Add book") }
            Spacer(Modifier.height(8.dp))
            Text("Saved books", style = MaterialTheme.typography.titleMedium)
        }
        items(books, key = { it.id }) { book ->
            ElevatedCard(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(14.dp)) {
                    Text(book.name, fontWeight = FontWeight.Bold)
                    Text("${book.code} · ${book.author}")
                    Text(if (book.available) "Available" else "Not available")
                    TextButton(onClick = { viewModel.delete(book) }) { Text("Delete") }
                }
            }
        }
    }
}

@Composable
private fun TaskScreen(viewModel: TaskViewModel) {
    val tasks by viewModel.tasks.collectAsState()
    var editingIndex by remember { mutableStateOf<Int?>(null) }
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf("") }
    var done by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    fun resetForm() {
        editingIndex = null
        title = ""
        content = ""
        dueDate = ""
        done = false
        error = null
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Tasks", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(title, { title = it }, label = { Text("Title") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(content, { content = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(dueDate, { dueDate = it }, label = { Text("Due date") }, modifier = Modifier.fillMaxWidth())
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(done, { done = it })
                Text("Completed")
            }
            error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = {
                        if (viewModel.save(editingIndex, TaskItem(title.trim(), content.trim(), dueDate.trim(), done))) {
                            resetForm()
                        } else {
                            error = "Title and due date are required."
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) { Text(if (editingIndex == null) "Add task" else "Update") }
                if (editingIndex != null) {
                    OutlinedButton(onClick = { resetForm() }, modifier = Modifier.weight(1f)) { Text("Cancel") }
                }
            }
            Spacer(Modifier.height(8.dp))
            Text("Task list", style = MaterialTheme.typography.titleMedium)
        }
        itemsIndexed(tasks) { index, task ->
            ElevatedCard(
                modifier = Modifier.fillMaxWidth().clickable {
                    editingIndex = index
                    title = task.title
                    content = task.content
                    dueDate = task.dueDate
                    done = task.isDone
                }
            ) {
                Column(Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text(task.title, fontWeight = FontWeight.Bold)
                            if (task.content.isNotBlank()) Text(task.content)
                            Text("Due: ${task.dueDate}")
                        }
                        Checkbox(task.isDone, { viewModel.toggle(index, it) })
                    }
                    TextButton(onClick = { viewModel.delete(index) }) { Text("Delete") }
                }
            }
        }
    }
}

@Composable
private fun StudentScreen(viewModel: StudentViewModel) {
    val students by viewModel.students.collectAsState()
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("Regular") }
    var message by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Students", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(name, { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(phone, { phone = it }, label = { Text("Phone") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(email, { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(type == "Regular", { type = "Regular" })
                Text("Regular")
                Spacer(Modifier.width(16.dp))
                RadioButton(type == "Part-Time", { type = "Part-Time" })
                Text("Part-Time")
            }
            message?.let {
                Text(
                    it,
                    color = if (it.startsWith("Added")) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                )
            }
            Button(
                onClick = {
                    val student = Student(name, phone, email, type)
                    if (viewModel.add(student)) {
                        message = "Added student."
                        name = ""; phone = ""; email = ""; type = "Regular"
                    } else {
                        message = "Enter a name, phone number, and valid email."
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Add student") }
            Spacer(Modifier.height(8.dp))
            Text("Student list", style = MaterialTheme.typography.titleMedium)
        }
        items(students) { student ->
            ElevatedCard(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(14.dp)) {
                    Text(student.name, fontWeight = FontWeight.Bold)
                    Text(student.email)
                    Text("${student.phone} · ${student.type}")
                }
            }
        }
    }
}
