# GitHub Repository Search App

This is a simple native Android app that allows users to search GitHub repositories using the GitHub API. The app demonstrates a clean architecture using MVVM, Hilt for dependency injection, and Retrofit for networking.

## Features
- Incremental search for GitHub repositories.
- Handles API errors and network issues.
- Clean architecture using MVVM.
- Unit tests for ViewModel and Retrofit.
- UI tests using Espresso.

## Requirements
- Android Studio
- Min SDK 21
- Retrofit, Hilt

## Installation
1. Clone the repository:
    ```bash
    git clone https://github.com/tariqul00/github-search-app.git
    ```
2. Open the project in Android Studio.
3. Build the project and run it on an emulator or physical device.

## Running Tests
- To run **Unit tests**, execute the following command in Android Studio:
    ```bash
    ./gradlew test
    ```
- To run **UI tests**, use the following command:
    ```bash
    ./gradlew connectedAndroidTest
    ```

## Architecture
The project follows the **MVVM** pattern and uses **Hilt** for dependency injection.

## API
This app uses the GitHub API to search for repositories. You can find the API documentation [here](https://developer.github.com/v3/search).

## License
This project is licensed under the MIT License.
