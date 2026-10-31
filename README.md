# Android Campus Companion

A consolidated Android portfolio project built with **Kotlin, Jetpack Compose, Room, MVVM-style ViewModels, StateFlow, coroutines, and Material 3**.

This repository refactors and combines three Android projects I originally developed separately: a Room-backed library manager, a task manager, and a student manager. The portfolio version presents them as one coherent application with a shared navigation shell and cleaner project structure.

## Features

### Library
- Add books with code, title, author, and availability
- Persist books locally with Room
- Repository abstraction over DAO operations
- Reactive book list with `Flow` / `StateFlow`
- Delete saved books

### Tasks
- Add, edit, complete, and delete tasks
- Track title, description, due date, and completion state
- ViewModel-owned UI state exposed through `StateFlow`

### Students
- Add student records with name, phone, email, and study type
- Basic form validation
- ViewModel-owned list state using `StateFlow`

## Architecture

```text
Compose UI
   ↓
ViewModels
   ↓
Repository (Library feature)
   ↓
Room DAO / local database
```

Task and student features intentionally keep their original lightweight in-memory `StateFlow` approach, while the library feature demonstrates persistent local data with Room.

## Tech Stack

- Kotlin
- Jetpack Compose
- Material 3
- Android ViewModel
- Kotlin Coroutines
- Flow / StateFlow
- Room Database
- Repository pattern
- JUnit

## Run

1. Clone the repository.
2. Open it in Android Studio.
3. Sync Gradle.
4. Run the `app` configuration on an Android emulator or device (API 24+).

No API keys, accounts, or external services are required.

## Testing

The project includes unit tests for reusable input-validation rules. Additional UI/instrumentation coverage can be added around the Compose flows.

## Portfolio Note

This is a portfolio consolidation of Android coursework/projects I developed, reorganized into one standalone application. It does not claim that the original assignments were submitted as a single application.
