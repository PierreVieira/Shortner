# Shortener

A modern, cross-platform URL shortener application built with Kotlin Multiplatform and Jetpack Compose. This app allows users to shorten long URLs, manage their shortened links, and customize their experience with various themes and Material You integration.

## 🚀 Features

### Core Functionality
- **URL Shortening**: Convert long URLs into shorter, more manageable links
- **Link Management**: View, copy, and delete your shortened links
- **Bulk Operations**: Delete all links at once with confirmation
- **Link History**: Keep track of all your shortened URLs with timestamps
- **Copy to Clipboard**: One-tap copying of shortened URLs

### Customization & Theming
- **Theme Selection**: Choose between Light, Dark, or System theme
- **Material You Integration**: Dynamic color theming that adapts to your device's wallpaper (Android)
- **Responsive Design**: Optimized layouts for different screen sizes
- **Modern UI**: Built with Material 3 design principles

### Cross-Platform Support
- **Android**: Native Android app with Material You support
- **iOS**: Native iOS app with SwiftUI integration
- **Desktop**: JVM-based desktop application for Windows, macOS, and Linux

## 🛠️ Technology Stack

### Core Technologies
- **Kotlin Multiplatform**: Shared business logic across all platforms
- **Jetpack Compose Multiplatform**: Modern declarative UI framework
- **Kotlin 2.2.20**: Latest Kotlin version with enhanced performance
- **Gradle 8.13.0**: Build system with Kotlin DSL

### Architecture & Patterns
- **Clean Architecture**: Separation of concerns with data, domain, and presentation layers
- **MVVM Pattern**: Model-View-ViewModel architecture with Compose
- **Repository Pattern**: Data abstraction layer for consistent data access
- **Dependency Injection**: Koin for lightweight dependency management

### Libraries & Frameworks
- **Navigation**: Jetpack Navigation Compose for type-safe navigation
- **Database**: Room with SQLite for local data persistence
- **Networking**: Ktor client for HTTP requests
- **Serialization**: Kotlinx Serialization for JSON handling
- **Coroutines**: Kotlinx Coroutines for asynchronous programming
- **DataStore**: Android DataStore for preferences storage
- **Material 3**: Latest Material Design components

### Platform-Specific Features
- **Android**: Material You dynamic theming, edge-to-edge display
- **iOS**: SwiftUI integration, native iOS navigation
- **Desktop**: Native window management, platform-specific distributions

## 📁 Project Structure

* [/composeApp](./composeApp/src) contains the shared Compose Multiplatform application code:
  - [commonMain](./composeApp/src/commonMain/kotlin) - Shared code for all platforms
  - [androidMain](./composeApp/src/androidMain/kotlin) - Android-specific implementations
  - [iosMain](./composeApp/src/iosMain/kotlin) - iOS-specific implementations  
  - [jvmMain](./composeApp/src/jvmMain/kotlin) - Desktop/JVM-specific implementations

* [/core](./core) - Shared core modules:
  - `network` - HTTP client and API services
  - `model` - Data models and routes
  - `provider` - Database, dependency injection, and data store providers
  - `utils` - Utility functions and extensions

* [/feature](./feature) - Feature modules following clean architecture:
  - `links` - URL shortening and management features
  - `theme_selection` - Theme customization
  - `material_you` - Dynamic theming support

* [/ui](./ui) - Shared UI components and theming:
  - `components` - Reusable UI components
  - `navigation` - Navigation setup and routing
  - `theme` - App theming and styling
  - `utils` - UI utility functions

* [/iosApp](./iosApp/iosApp) - iOS application entry point and SwiftUI integration

## 🚀 Getting Started

### Prerequisites
- **Android Studio** (latest version recommended)
- **Xcode** (for iOS development, macOS only)
- **JDK 21** or higher
- **Kotlin Multiplatform Mobile Plugin** for Android Studio

### Build and Run Applications

#### Android Application
To build and run the development version of the Android app:

**Using IDE:**
- Open the project in Android Studio
- Select the Android run configuration from the run widget in the toolbar
- Click the run button

**Using Terminal:**
- on macOS/Linux:
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```
- on Windows:
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```

#### Desktop (JVM) Application
To build and run the development version of the desktop app:

**Using IDE:**
- Select the Desktop run configuration from the run widget in the toolbar
- Click the run button

**Using Terminal:**
- on macOS/Linux:
  ```shell
  ./gradlew :composeApp:run
  ```
- on Windows:
  ```shell
  .\gradlew.bat :composeApp:run
  ```

#### iOS Application
To build and run the development version of the iOS app:

**Using IDE:**
- Select the iOS run configuration from the run widget in the toolbar
- Click the run button

**Using Xcode:**
- Open the [/iosApp](./iosApp) directory in Xcode
- Select your target device or simulator
- Click the run button

## 🏗️ Development

### Project Setup
1. Clone the repository
2. Open the project in Android Studio
3. Sync the project with Gradle files
4. Wait for all dependencies to be downloaded

### Code Structure
The project follows a modular architecture with clear separation of concerns:
- **Feature modules** contain complete features with their own data, domain, and presentation layers
- **Core modules** provide shared functionality across features
- **UI modules** contain reusable components and theming

### Key Dependencies
- **Compose Multiplatform 1.9.1** - UI framework
- **Koin 4.1.1** - Dependency injection
- **Ktor 3.3.1** - HTTP client
- **Room 2.8.3** - Local database
- **Navigation Compose 2.9.1** - Navigation

## 📱 Platform Support

| Platform | Status | Features |
|----------|--------|----------|
| Android | ✅ Full Support | Material You, Edge-to-edge, Native navigation |
| iOS | ✅ Full Support | SwiftUI integration, Native navigation |
| Desktop | ✅ Full Support | Windows, macOS, Linux distributions |

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test on all target platforms
5. Submit a pull request

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html) and [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/).
