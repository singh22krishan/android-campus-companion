package com.krishansingh.campuscompanion.features.tasks

import androidx.lifecycle.ViewModel
import com.krishansingh.campuscompanion.validation.Validators
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class TaskItem(
    val title: String,
    val content: String,
    val dueDate: String,
    val isDone: Boolean = false
)

class TaskViewModel : ViewModel() {
    private val _tasks = MutableStateFlow<List<TaskItem>>(emptyList())
    val tasks: StateFlow<List<TaskItem>> = _tasks

    fun save(index: Int?, task: TaskItem): Boolean {
        if (!Validators.validTask(task.title, task.dueDate)) return false
        _tasks.update { current ->
            if (index == null) {
                current + task
            } else {
                current.toMutableList().also { list ->
                    if (index in list.indices) list[index] = task
                }
            }
        }
        return true
    }

    fun toggle(index: Int, done: Boolean) {
        _tasks.update { current ->
            current.toMutableList().also { list ->
                if (index in list.indices) list[index] = list[index].copy(isDone = done)
            }
        }
    }

    fun delete(index: Int) {
        _tasks.update { current -> current.filterIndexed { i, _ -> i != index } }
    }
}
