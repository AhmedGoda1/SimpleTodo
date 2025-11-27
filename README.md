# SimpleTodo

A simple to-do list Android app built with **Kotlin** and **Jetpack Compose** for my IT assignment.

## Features

- ✅ Add tasks with a title and optional description
- ✅ Mark tasks as done / not done
- ✅ Delete tasks
- ✅ Dark / light theme toggle in Settings
- ✅ Data saved locally using **Room** (persists after closing the app)

## Tech stack

- **Language:** Kotlin
- **UI:** Jetpack Compose + Material 3
- **Navigation:** Navigation Compose
- **Architecture:** ViewModel + state (Compose `mutableStateOf`)
- **Persistence:** Room (Task, TaskDao, TaskDatabase, TaskRepository)
- **Testing:** JUnit unit test (`ValidationUtilsTest`) for input validation

## Screens

1. **Task List Screen**
    - Shows all tasks from Room database
    - Buttons:
        - `Add` → navigates to Add Task screen
        - `Settings` → navigates to Settings screen

2. **Add Task Screen**
    - Text fields for **Title** and **Description**
    - Validation: title cannot be empty (shows error message)
    - Buttons:
        - `Save` → saves to Room via `TaskViewModel`
        - `Cancel` → goes back without saving

3. **Settings Screen**
    - Dark theme **Switch**
    - `Back` button to return to the task list

## Testing

- `isTitleValid()` helper function validates task titles
- Unit tests in `ValidationUtilsTest`:
    - Empty / blank titles are invalid
    - Non-empty titles are valid

## How to run

1. Open the project in **Android Studio**.
2. Let Gradle sync.
3. Run the app on an emulator or device.

## APK / Release

The release APK is available in the GitHub Releases section.
