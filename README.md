# C-Quiz

An Android quiz app that tests your knowledge of the C programming language.

## Features

- Splash screen, then a choice to log in or register
- Registration and login, with accounts stored on the device in SharedPreferences
- Game rules screen before the quiz starts
- Multiple-choice C questions; some have more than one correct answer
- Result screen with your score
- Score history of previous games, also kept on the device

## Build and run

Requirements: Android Studio (Hedgehog or newer) or JDK 17 with the Android SDK, `compileSdk` 34.

```bash
./gradlew assembleDebug    # APK in app/build/outputs/apk/debug/
./gradlew installDebug     # install on a connected device or emulator
```

Minimum Android version: API 30 (Android 11).

## Author

Vivek Datla
