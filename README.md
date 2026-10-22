<h1 align="center">🌸 Bloom</h1>

<p align="center">
  A personalized wellness onboarding app for Android, built with <b>Kotlin</b> and <b>Jetpack Compose</b>,<br/>
  with persistent preferences and Google AdMob monetization.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin"/>
  <img src="https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white" alt="Jetpack Compose"/>
  <img src="https://img.shields.io/badge/Material%203-6750A4?style=for-the-badge&logo=materialdesign&logoColor=white" alt="Material 3"/>
  <img src="https://img.shields.io/badge/Hilt-DI-34A853?style=for-the-badge" alt="Hilt"/>
  <img src="https://img.shields.io/badge/AdMob-EA4335?style=for-the-badge&logo=googleadsense&logoColor=white" alt="AdMob"/>
</p>

---

## 📑 Table of Contents

- [Demo Video](#-demo-video)
- [Screenshots](#-screenshots)
- [Features](#-features)
- [Tech Stack](#️-tech-stack)
- [Application Flow](#-application-flow)
- [Architecture](#️-architecture)
- [Project Structure](#-project-structure)
- [Setup Instructions](#-setup-instructions)
- [Ad Implementation](#-ad-implementation)
- [Testing Checklist](#-testing-checklist)
- [Deliverables](#-deliverables)
- [Assumptions and Limitations](#️-assumptions-and-limitations)
- [Privacy and Configuration](#-privacy-and-configuration)
- [Author](#-author)

---

## 🎬 Demo Video

<p align="center">
  <a href="https://youtu.be/KOsRwjGP5Yc" target="_blank">
    <img src="https://img.youtube.com/vi/KOsRwjGP5Yc/maxresdefault.jpg" alt="Watch the Bloom demo on YouTube" width="560"/>
  </a>
  <br/>
  <a href="https://youtu.be/KOsRwjGP5Yc"><b>▶️ Watch the full app demo on YouTube</b></a>
</p>

---

## 📸 Screenshots

<table align="center">
  <tr>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/9fd39910-24ac-44ce-91ac-5dc5af42ddca" width="250" alt="Welcome screen"/>
      <br/><sub><b>Welcome</b></sub>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/abb67c5c-7a77-4cb3-be77-33d74131eaac" width="250" alt="About You screen"/>
      <br/><sub><b>About You</b></sub>
    </td>
  </tr>
  <tr>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/327a7e43-d16f-43ac-9166-6b21ad802445" width="250" alt="Goals screen"/>
      <br/><sub><b>Goals</b></sub>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/19cb1574-9132-4d2b-9919-0ce1dc802557" width="250" alt="Reminders screen"/>
      <br/><sub><b>Reminders</b></sub>
    </td>
  </tr>
  <tr>
    <td align="center" colspan="2">
      <img src="https://github.com/user-attachments/assets/e2cf064a-d80a-4c25-b2a4-c4e9b7b70253" width="250" alt="Home screen"/>
      <br/><sub><b>Home</b></sub>
    </td>
  </tr>
</table>

---

## ✨ Features

- **Onboarding Flow:** A four-step experience covering welcome, personal details, goals, and reminders.
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

1. The app launches on the Splash screen.
2. It checks the onboarding completion flag stored in DataStore.
3. If onboarding is not completed, the user enters the onboarding flow.
4. The user moves through four screens: **Welcome → About You → Goals → Reminders**.
5. On the final screen, the user finishes onboarding.
6. The app saves the onboarding information and completion flag.
7. The user navigates to the Home screen.

### Returning User

1. The app launches on the Splash screen.
2. DataStore indicates onboarding has already been completed.
3. The onboarding screens are skipped.
4. The app continues to the Home screen and its configured ad flow.

> The completion flag is persisted locally, so it survives app restarts.

## 🏗️ Architecture

Bloom uses **MVVM (Model–View–ViewModel)** with a clear separation between UI, state management, and persistent data operations.

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
              │   Onboarding / Home   │
              └───────────┬───────────┘
                          │
                 ┌────────▼─────────┐
                 │  Onboarding UI   │
                 │  Onboarding      │
                 │  ViewModel       │
                 └────────┬─────────┘
                          │
                 ┌────────▼─────────┐
                 │  Onboarding      │
                 │  DataStore       │
                 └──────────────────┘
```

### Presentation Layer

Contains the Compose screens and ViewModels. Responsibilities:

- Displaying onboarding and Home UI.
- Maintaining the current onboarding page.
- Managing user input, selected goals, and reminder preferences.
- Exposing observable state through StateFlow.
- Initiating onboarding completion and navigation events.

### Data Layer

Uses Jetpack DataStore Preferences to store:

- Onboarding completion status
- User name
- Selected wellness goals
- Reminder time
- Reminder enabled status

Onboarding details and the completion flag are saved within the **same DataStore transaction**.

### Dependency Injection

Hilt provides dependencies such as the onboarding DataStore to the relevant ViewModels, reducing manual object creation and keeping dependencies easy to manage.

## 📂 Project Structure

> A logical overview. Exact filenames and package locations may differ slightly from the repository.

```text
Bloom-App/
├── app/
│   └── src/
│       └── main/
│           ├── java/com/mohit/mohitbloomapp/
│           │   ├── ads/
│           │   │   ├── AdMobConfig.kt
│           │   │   └── AppOpenAdManager.kt
│           │   ├── data/
│           │   │   └── onboarding/
│           │   │       └── OnboardingDataStore.kt
│           │   ├── navigation/
│           │   │   ├── AppNavigation.kt
│           │   │   └── Routes.kt
│           │   ├── presentation/
│           │   │   ├── onboarding/
│           │   │   │   ├── screen/
│           │   │   │   └── viewmodel/
│           │   │   └── screens/
│           │   │       ├── SplashScreen.kt
│           │   │       └── home/
│           │   └── MyApp.kt
│           └── AndroidManifest.xml
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── README.md
```

## 🚀 Setup Instructions

### Prerequisites

- Android Studio
- A compatible JDK configured through Android Studio
- Android SDK versions required by the project
- An Android emulator or a physical device
- Git

### 1. Clone the Repository

```bash
git clone https://github.com/mohitdamke/Bloom-App.git
cd Bloom-App
```

### 2. Open the Project

1. Launch Android Studio and select **Open**.
2. Choose the cloned `Bloom-App` directory.
3. Allow Gradle to sync.
4. Install any SDK components Android Studio requests.

### 3. Configure Google Mobile Ads

Verify that the AdMob configuration contains the appropriate ad unit IDs. The application ID must also be configured in `AndroidManifest.xml`. For testing, use Google's official sample IDs listed in the [Ad Implementation](#-ad-implementation) section.

### 4. Run the Application

Run the `app` configuration from Android Studio, or build the debug APK with Gradle:

```bash
./gradlew assembleDebug        # macOS / Linux
gradlew.bat assembleDebug      # Windows
```

The APK is generated at `app/build/outputs/apk/debug/app-debug.apk`.

### 5. Install the APK Using ADB

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

## 📢 Ad Implementation

Bloom integrates two Google AdMob formats through the Google Mobile Ads SDK: **App Open Ads** and **Banner Ads**.

### App Open Ads

Full-screen ads shown when users open or return to the app. An `AppOpenAdManager` coordinates loading and display. It handles:

- Requesting an App Open Ad and tracking loading state.
- Checking whether an ad is available.
- Discarding ads that exceed the configured validity period.
- Preventing multiple App Open Ads from showing simultaneously.
- Handling ad dismissal and display failures.
- Requesting another ad after the current ad flow finishes.

The app lifecycle detects foreground transitions, and the current Activity is supplied when an ad needs to be displayed. The onboarding completion flag distinguishes first-time from returning users.

**Expected behavior:**

- First-time users complete onboarding before the returning-user ad flow.
- Returning users can enter the configured App Open Ad flow.
- If an ad is unavailable, the app continues without indefinitely blocking navigation.
- The app avoids showing an ad using a destroyed or finishing Activity.

### Banner Ads

Banner Ads are displayed within the Home screen UI, separate from the main content. Actual size and placement are determined by the Home screen implementation.

### Test Ad IDs

| Ad Format | Google Test Ad Unit ID |
|---|---|
| App Open Ad | `ca-app-pub-3940256099942544/9257395921` |
| Banner Ad | `ca-app-pub-3940256099942544/6300978111` |

Google's sample App ID for Android:

```text
ca-app-pub-3940256099942544~3347511713
```

> **Important:** These are Google's official sample IDs, not proof that the current repository or APK uses them. Verify `AdMobConfig.kt` and `AndroidManifest.xml` before submission. Before a production release, configure your own AdMob application ID and ad unit IDs.

**Google documentation:**

- [App Open Ads for Android](https://developers.google.com/admob/android/app-open)
- [Banner Ads for Android](https://developers.google.com/admob/android/banner)
- [Google Mobile Ads test ads](https://developers.google.com/admob/android/test-ads)

## 🧪 Testing Checklist

### Onboarding

- [ ] A fresh installation opens the onboarding flow.
- [ ] The user can navigate forward and backward between screens.
- [ ] The progress indicator updates correctly.
- [ ] The user cannot finish onboarding without entering a name.
- [ ] Selected goals and reminder preferences are saved correctly.
- [ ] The completion flag is saved only after details are saved successfully.
- [ ] The app navigates to Home after successful completion.
- [ ] Restarting the app does not show onboarding again.
- [ ] Rotating the device does not reset the current onboarding page.

### Home Screen

- [ ] The Home screen opens after onboarding.
- [ ] It renders correctly on the target device.
- [ ] Home content remains usable when an ad is unavailable.
- [ ] The banner does not cover essential UI.

### App Open Ads

- [ ] The Mobile Ads SDK initializes successfully.
- [ ] An App Open Ad request is made and logs show success or failure.
- [ ] A loaded test ad can be displayed and dismissed.
- [ ] Failed or unavailable ads do not leave the app stuck.
- [ ] Rotation does not cause duplicate ad displays.
- [ ] Returning to the foreground follows the intended ad behavior.

### Banner Ads

- [ ] A banner request is made on the intended screen.
- [ ] A test banner loads, renders, and fits the layout.
- [ ] The Home screen remains usable when the banner fails to load.

## 📦 Deliverables

| Deliverable | Description | Status |
|---|---|---|
| Android Studio project | Complete Kotlin source and Gradle configuration | ✅ In this repository |
| APK | Buildable APK for installation and evaluation | Generate and attach the final APK |
| Git repository | Version-controlled source | [Bloom-App](https://github.com/mohitdamke/Bloom-App) |
| Demo video | Walkthrough of the app | [Watch on YouTube](https://youtu.be/KOsRwjGP5Yc) |
| Onboarding flow | Four-step flow with saved preferences and completion status | Verify using the checklist |
| Home screen | Accessible after onboarding | Verify on device or emulator |
| App Open Ad | Loading, display, dismissal, and failure handling | Verify with Google's test ad ID |
| Banner Ad | Loading and rendering | Verify with Google's test ad ID |
| README | Setup, architecture, ad approach, test IDs, assumptions, limitations | This document |

### Building the APK

```bash
./gradlew assembleDebug
```

Output: `app/build/outputs/apk/debug/app-debug.apk`

Attach the generated APK to your submission or publish it as a GitHub Release asset. Building an APK does not upload it to GitHub automatically. A debug APK is suitable for development and evaluation; for a release APK, build the release variant and configure signing.

## ⚠️ Assumptions and Limitations

- **Local persistence:** Onboarding details are stored locally using DataStore. There is no remote account or cloud sync.
- **Onboarding completion:** The flag decides whether onboarding is shown again. Clearing app data resets it.
- **Ad availability:** Requests can fail due to network connectivity, SDK initialization, inventory, or ad unit configuration.
- **Test advertisements:** Google's sample IDs are for development, not production.
- **Ad display timing:** App Open Ads should never indefinitely delay navigation when no ad is ready.
- **Activity lifecycle:** Full-screen ads must be shown only from a valid Activity, and lifecycle changes must be handled to avoid duplicate display attempts.
- **Banner layout:** Dimensions and placement depend on the Home screen implementation and device space.
- **Reminder behavior:** Saving a reminder time and enabled flag does not schedule Android notifications or background work; that must be implemented separately.
- **Production readiness:** Test onboarding, both ad formats, rotation, and failure cases before considering the deliverables complete.

## 🔒 Privacy and Configuration

Do not commit private credentials, signing keys, or other sensitive configuration files. Use the appropriate AdMob configuration for each build, and review the applicable Google Mobile Ads policies before distributing the app.

## 👨‍💻 Author

**Mohit Damke**

- GitHub: [@mohitdamke](https://github.com/mohitdamke)
- Project: [Bloom-App](https://github.com/mohitdamke/Bloom-App)

---

<p align="center">Made with ❤️ using Kotlin & Jetpack Compose</p>
