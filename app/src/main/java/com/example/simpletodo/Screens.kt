package com.example.simpletodo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simpletodo.data.Task
import com.example.simpletodo.viewmodel.TaskViewModel

// ---------- TASK LIST SCREEN ----------

@Composable
fun TaskListScreen(
    viewModel: TaskViewModel,
    onAddTaskClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val tasks = viewModel.tasks

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "My Tasks", style = MaterialTheme.typography.titleLarge)
            Row {
                Button(onClick = onSettingsClick) {
                    Text(text = "Settings")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = onAddTaskClick) {
                    Text(text = "Add")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (tasks.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No tasks yet. Tap Add to create one.")
            }
        } else {
            LazyColumn {
                items(tasks) { task ->
                    TaskItem(
                        task = task,
                        onToggleDone = { viewModel.toggleDone(task) },
                        onDelete = { viewModel.deleteTask(task) }
                    )
                }
            }
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onToggleDone: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = task.isDone,
            onCheckedChange = { onToggleDone() }
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.bodyLarge
            )
            if (task.description.isNotBlank()) {
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        Button(onClick = onDelete) {
            Text(text = "Delete")
        }
    }
}

// Screen for adding a new task

@Composable
fun AddEditTaskScreen(
    viewModel: TaskViewModel,
    onBackClick: () -> Unit
) {
    val title = viewModel.title
    val description = viewModel.description
    val titleError = viewModel.titleError

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Add Task", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { viewModel.onTitleChange(it) },
            label = { Text("Title") },
            isError = titleError != null,
            modifier = Modifier.fillMaxWidth()
        )

        if (titleError != null) {
            Text(
                text = titleError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { viewModel.onDescriptionChange(it) },
            label = { Text("Description (optional)") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(onClick = {
                val success = viewModel.addTask()
                if (success) {
                    onBackClick()
                }
            }) {
                Text(text = "Save")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = onBackClick) {
                Text(text = "Cancel")
            }
        }
    }
}

// Settings screen (dark mode switch)

@Composable
fun SettingsScreen(
    darkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Settings", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Dark theme")
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = darkTheme,
                onCheckedChange = { onDarkThemeChange(it) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onBackClick) {
            Text(text = "Back")
        }
    }
}
