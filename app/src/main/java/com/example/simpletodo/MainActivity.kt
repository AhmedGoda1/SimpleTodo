package com.example.simpletodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simpletodo.ui.theme.SimpleTodoTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simpletodo.viewmodel.TaskViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoApp()
        }
    }
}


@Composable
fun TodoApp(
    taskViewModel: TaskViewModel = viewModel()
) {
    val navController = rememberNavController()

    // simple dark theme toggle
    var darkTheme by remember { mutableStateOf(false) }

    SimpleTodoTheme(darkTheme = darkTheme) {
        Surface(color = MaterialTheme.colorScheme.background) {
            NavHost(
                navController = navController,
                startDestination = "task_list"
            ) {
                composable("task_list") {
                    TaskListScreen(
                        viewModel = taskViewModel,
                        onAddTaskClick = {
                            navController.navigate("add_edit_task")
                        },
                        onSettingsClick = {
                            navController.navigate("settings")
                        }
                    )
                }

                composable("add_edit_task") {
                    AddEditTaskScreen(
                        viewModel = taskViewModel,
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }

                composable("settings") {
                    SettingsScreen(
                        darkTheme = darkTheme,
                        onDarkThemeChange = { darkTheme = it },
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}

