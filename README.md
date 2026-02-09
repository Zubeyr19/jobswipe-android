# JobSwipe

A Tinder-style job search app for Android. Swipe right on jobs you like to save them and open the application page.

## Features

- **Swipe Right** - Save the job and open the application URL
- **Swipe Left** - Skip the job
- **Saved Jobs** - View all jobs you've saved
- **Clean UI** - Modern Material Design 3 interface

## Tech Stack

- **Kotlin** - Primary language
- **Jetpack Compose** - Modern declarative UI
- **Room Database** - Local data persistence
- **MVVM Architecture** - Clean separation of concerns
- **Coroutines + Flow** - Async operations and reactive data

## Project Structure

```
app/src/main/java/com/jobswipe/
├── data/
│   ├── model/          # Data classes (Job)
│   ├── local/          # Room database & DAO
│   └── repository/     # Data repository
├── ui/
│   ├── components/     # Reusable UI components
│   ├── screens/        # App screens
│   └── theme/          # Material theme
├── viewmodel/          # ViewModels
└── MainActivity.kt     # Entry point
```

## Getting Started

1. Open in Android Studio
2. Sync Gradle
3. Run on emulator or device (API 26+)

## Screenshots

*Coming soon*

## Roadmap

- [ ] Connect to real job APIs (LinkedIn, Indeed)
- [ ] User profiles with CV upload
- [ ] Job filters (location, salary, type)
- [ ] Push notifications for new jobs
- [ ] Backend API integration
