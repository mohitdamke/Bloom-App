# 🌸 Bloom — Android App

Bloom is an Android application built with **Kotlin and Jetpack Compose**. It provides a personalized onboarding experience, a home screen, and Google AdMob advertising.

The project focuses on a clean Android implementation with ViewModel-based UI state management, persistent onboarding preferences, and App Open and Banner Ads.

## ✨ Features

- **Onboarding Flow:** A four-step onboarding experience covering welcome, personal details, goals, and reminders.
- **Personalization:** Collects the user's name, selected wellness goals, and reminder preferences.
- **Persistent Preferences:** Stores onboarding details and completion status using Jetpack DataStore.
- **Home Screen:** Opens after onboarding is completed.
- **App Open Ads:** Integrates Google AdMob App Open Ads.
- **Banner Ads:** Integrates Google AdMob Banner Ads.
- **Lifecycle-Aware UI:** Uses ViewModels and lifecycle-aware state collection to manage UI state.
- **Dependency Injection:** Uses Hilt for dependency injection.

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Kotlin | Primary programming language |
| Jetpack Compose | Declarative UI |
| Material 3 | UI components and styling |
| ViewModel | UI state and business logic |
| Kotlin Coroutines | Asynchronous operations |
| Kotlin StateFlow | Observable UI state |
| Jetpack DataStore | Persistent onboarding preferences |
| Navigation Compose | Screen navigation |
| Hilt | Dependency injection |
| Google Mobile Ads SDK | App Open Ads and Banner Ads |
| Gradle Kotlin DSL | Build configuration |

## 📱 Application Flow

### First-Time User

1. The application launches on the Splash screen.
2. The app checks the onboarding completion flag stored in DataStore.
3. If onboarding has not been completed, the user enters the onboarding flow.
4. The user moves through four screens:
   - Welcome
   - About You
   - Goals
   - Reminders
5. On the final screen, the user finishes onboarding.
6. The application saves the onboarding information and completion flag.
7. The user navigates to the Home screen.

### Returning User

1. The application launches on the Splash screen.
2. DataStore indicates that onboarding has already been completed.
3. The onboarding screens are skipped.
4. The app continues to the Home screen and its configured ad flow.

The onboarding completion flag is persisted locally, so it remains available after the application is closed or restarted.

## 🏗️ Architecture

Bloom uses **MVVM (Model–View–ViewModel)** with a separation between UI, state management, and persistent data operations.

### Architecture Overview

```text
                 ┌──────────────────┐
                 │    Application   │
                 └────────┬─────────┘
                          │
                 ┌────────▼─────────┐
                 │  App Navigation  │
                 └────────┬─────────┘
                          │
               ┌──────────▼──────────┐
               │    Splash Screen    │
               │   Splash ViewModel  │
               └──────────┬──────────┘
                          │
                 Check onboarding
                     completion
                          │
              ┌───────────▼───────────┐
              │  Onboarding / Home    │
              └───────────┬───────────┘
                          │
                 ┌────────▼─────────┐
                 │ Onboarding UI    │
                 │ Onboarding       │
                 │ ViewModel        │
                 └────────┬─────────┘
                          │
                 ┌────────▼─────────┐
                 │ Onboarding       │
                 │ DataStore        │
                 └──────────────────┘
```

### Presentation Layer

The presentation layer contains the Compose screens and ViewModels.

Responsibilities include:

- Displaying onboarding and Home UI.
- Maintaining the current onboarding page.
- Managing user input and selected goals.
- Managing reminder preferences.
- Exposing observable state through StateFlow.
- Initiating onboarding completion and navigation events.

### Data Layer

The onboarding data layer uses Jetpack DataStore Preferences.

It stores:

- Onboarding completion status.
- User name.
- Selected wellness goals.
- Reminder time.
- Reminder enabled status.

Onboarding details and the completion flag are saved within the same DataStore transaction.

### Dependency Injection

Hilt provides dependencies such as the onboarding DataStore to the relevant ViewModels.

This reduces manual object creation and makes dependencies easier to manage.

## 📂 Project Structure

The following is a logical overview of the relevant application components. Exact filenames and package locations may differ slightly from the repository.

```text
Bloom-App/
├── app/
│   └── src/
│       └── main/
│           ├── java/com/mohit/mohitbloomapp/
│           │   ├── ads/
│           │   │   ├── AdMobConfig.kt
│           │   │   └── AppOpenAdManager.kt
│           │   │
│           │   ├── data/
│           │   │   └── onboarding/
│           │   │       └── OnboardingDataStore.kt
│           │   │
│           │   ├── navigation/
│           │   │   ├── AppNavigation.kt
│           │   │   └── Routes.kt
│           │   │
│           │   ├── presentation/
│           │   │   ├── onboarding/
│           │   │   │   ├── screen/
│           │   │   │   └── viewmodel/
│           │   │   │
│           │   │   └── screens/
│           │   │       ├── SplashScreen.kt
│           │   │       └── home/
│           │   │
│           │   └── MyApp.kt
│           │
│           └── AndroidManifest.xml
│
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── README.md
```

## 🚀 Setup Instructions

### Prerequisites

Install the following:

- Android Studio.
- A compatible JDK configured through Android Studio.
- Android SDK versions required by the project.
- An Android emulator or a physical Android device.
- Git.

### 1. Clone the Repository

```bash
git clone https://github.com/mohitdamke/Bloom-App.git
```

Navigate to the project directory:

```bash
cd Bloom-App
```

### 2. Open the Project

1. Launch Android Studio.
2. Select **Open**.
3. Choose the cloned `Bloom-App` directory.
4. Allow Gradle to sync.
5. Install any SDK components requested by Android Studio.

### 3. Configure Google Mobile Ads

Verify that the app's AdMob configuration contains the appropriate ad unit IDs.

The Google Mobile Ads SDK also requires the application ID to be configured in `AndroidManifest.xml`.

For testing, use Google's official sample IDs listed in the advertising section below.

If the project uses a custom AdMob application ID, verify that it is configured correctly before running the application.

### 4. Run the Application

Run the `app` configuration from Android Studio on an emulator or a connected Android device.

Alternatively, build and install the debug APK using Gradle:

```bash
./gradlew assembleDebug
```

On Windows:

```bat
gradlew.bat assembleDebug
```

The debug APK is normally generated at:

```text
app/build/outputs/apk/debug/app-debug.apk
```

### 5. Install the APK Using ADB

If Android Debug Bridge is configured:

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

## 📢 Ad Implementation

Bloom integrates two Google AdMob ad formats:

1. App Open Ads.
2. Banner Ads.

The Google Mobile Ads SDK is used to request and display advertisements.

### App Open Ads

App Open Ads are full-screen advertisements designed to appear when users open or return to an application.

The implementation uses an `AppOpenAdManager` to coordinate ad loading and display.

The manager handles:

- Requesting an App Open Ad.
- Tracking whether an ad is loading.
- Checking whether an ad is available.
- Discarding ads that exceed the configured validity period.
- Preventing multiple App Open Ads from being displayed simultaneously.
- Handling ad dismissal and display failures.
- Requesting another ad after the current ad flow finishes.

The app lifecycle is used to detect foreground transitions. The current Activity is supplied when an ad needs to be displayed.

The onboarding completion flag is also used to distinguish first-time users from returning users.

**Expected behavior:**

- First-time users complete onboarding before the returning-user ad flow.
- Returning users can enter the configured App Open Ad flow.
- If an ad is unavailable, the application should continue without indefinitely blocking navigation.
- The app should avoid attempting to display an ad using a destroyed or finishing Activity.

Actual display behavior depends on ad availability, SDK initialization, network conditions, and the configured ad unit.

### Banner Ads

Banner Ads are displayed within the Home screen UI.

The Banner Ad implementation uses the Google Mobile Ads SDK to request an ad and render it in the available layout space.

Banner Ads are intended to remain separate from the main Home screen content.

The actual banner size and placement are determined by the implementation in the Home screen.

### Test Ad IDs

Use Google's official sample ad unit IDs during development.

| Ad Format | Google Test Ad Unit ID |
|---|---|
| App Open Ad | `ca-app-pub-3940256099942544/9257395921` |
| Banner Ad | `ca-app-pub-3940256099942544/6300978111` |

Google's sample App ID for Android is:

```text
ca-app-pub-3940256099942544~3347511713
```

**Important:** These are Google's official sample IDs, not proof that the current repository or submitted APK uses them. Verify `AdMobConfig.kt` and `AndroidManifest.xml` before submission.

Use test ads during development. Before a production release, configure the appropriate AdMob application ID and ad unit IDs for the intended release environment.

Google documentation:

- [App Open Ads for Android](https://developers.google.com/admob/android/app-open)
- [Banner Ads for Android](https://developers.google.com/admob/android/banner)
- [Google Mobile Ads test ads](https://developers.google.com/admob/android/test-ads)

## 🧪 Testing Checklist

Use this checklist to verify the application before submission.

### Onboarding

- [ ] A fresh installation opens the onboarding flow.
- [ ] The user can navigate forward between onboarding screens.
- [ ] The user can navigate backward.
- [ ] The progress indicator updates correctly.
- [ ] The user cannot finish onboarding without entering a name.
- [ ] The selected goals are saved correctly.
- [ ] Reminder preferences are saved correctly.
- [ ] The completion flag is saved only after onboarding details are saved successfully.
- [ ] The app navigates to Home after successful completion.
- [ ] Restarting the app does not show onboarding again after completion.
- [ ] Rotating the device does not unexpectedly reset the current onboarding page.

### Home Screen

- [ ] The Home screen opens after onboarding.
- [ ] The Home screen renders correctly on the target device.
- [ ] Home content remains usable when an ad is unavailable.
- [ ] The banner is placed correctly and does not cover essential UI.

### App Open Ads

- [ ] The Mobile Ads SDK initializes successfully.
- [ ] An App Open Ad request is made.
- [ ] Logs indicate whether the request succeeds or fails.
- [ ] A loaded test ad can be displayed.
- [ ] Dismissing the ad returns the user to the application.
- [ ] Failed or unavailable ads do not leave the app stuck.
- [ ] Rotation does not cause duplicate ad displays.
- [ ] Returning to the foreground follows the intended ad behavior.

### Banner Ads

- [ ] A banner request is made on the intended screen.
- [ ] A test banner can load and render.
- [ ] The banner fits the available layout.
- [ ] The rest of the Home screen remains usable when the banner fails to load.

## 📦 Deliverables

The following items are expected for submission.

| Deliverable | Description | Status |
|---|---|---|
| Android Studio project/source code | Complete Android project, including Kotlin source files and Gradle configuration | Available in the Git repository |
| APK | Buildable Android APK for installation and evaluation | Generate and attach the final APK |
| Git repository | Version-controlled project source | [Bloom-App](https://github.com/mohitdamke/Bloom-App) |
| Working onboarding flow | Four-step onboarding with saved user preferences and completion status | Verify using the testing checklist |
| Working Home Screen | Home screen accessible after onboarding | Verify on a device or emulator |
| Working App Open Ad | App Open Ad loading, display, dismissal, and failure handling | Verify with Google's test ad ID |
| Working Banner Ad | Banner Ad loading and rendering | Verify with Google's test ad ID |
| README | Setup, architecture, ad approach, test IDs, assumptions, and limitations | This document |

### Building the APK

Run:

```bash
./gradlew assembleDebug
```

The expected output is:

```text
app/build/outputs/apk/debug/app-debug.apk
```

Attach the generated APK to the submission or publish it as a GitHub Release asset.

A debug APK is suitable for development and evaluation. If a release APK is required, build the release variant and configure signing appropriately.

**Note:** Building an APK does not automatically upload it to GitHub. The APK must be attached separately or published as a release asset.

## ⚠️ Assumptions and Limitations

- **Local persistence:** Onboarding details are stored locally using DataStore. No remote user account or cloud synchronization is described by this implementation.
- **Onboarding completion:** The onboarding completion flag determines whether onboarding is shown again. Clearing application data resets this flag.
- **Ad availability:** Ad requests can fail because of network connectivity, SDK initialization, inventory availability, or ad unit configuration.
- **Test advertisements:** Google's sample ad IDs are intended for development and testing, not as the application's production ad units.
- **Ad display timing:** App Open Ads should not indefinitely delay navigation when no ad is ready.
- **Activity lifecycle:** Full-screen ads must be shown only from a valid Activity. Lifecycle changes must be handled to avoid duplicate display attempts.
- **Banner layout:** Banner dimensions and placement depend on the Home screen implementation and available device space.
- **Reminder behavior:** Saving a reminder time and enabled flag does not by itself schedule Android notifications or background work. Scheduling must be implemented separately if required.
- **Production readiness:** Test the onboarding flow, both ad formats, device rotation, and failure cases before considering the deliverables complete.

## 🔒 Privacy and Configuration

Do not commit private credentials, signing keys, or other sensitive configuration files to the repository.

Use the appropriate AdMob configuration for the intended build. Review the applicable Google Mobile Ads policies and requirements before distributing the application.

## 👨‍💻 Author

**Mohit Damke**

GitHub: [mohitdamke](https://github.com/mohitdamke)

Project: [Bloom-App](https://github.com/mohitdamke/Bloom-App)
