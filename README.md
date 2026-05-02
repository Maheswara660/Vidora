<p align="center">
  <img src="assets/App-icon.webp" width="140" height="140" alt="Vidora Logo">
</p>

<h1 align="center">Vidora</h1>

<p align="center">
  <b>Experience cinematic perfection with Vidora — a premium, high-performance Video Player for Android.</b><br>
  Built with the precision of Jetpack Compose and the elegance of Material 3.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Language-Kotlin%202.3.20-blue?style=for-the-badge&logo=kotlin" alt="Kotlin">
  <img src="https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose" alt="Compose">
  <img src="https://img.shields.io/badge/Android-16-3DDC84?style=for-the-badge&logo=android" alt="Android">
</p>

---

## 📝 About Vidora
Vidora is more than just a video player; it is a meticulously crafted multimedia suite designed for the modern Android ecosystem. It combines raw power with a "Premium Airy" design philosophy, featuring glassmorphism and smooth micro-animations. Whether you are watching local movies or managing a vast video library, Vidora provides an immersive and efficient experience.

---

## 🛠️ Technology Stack
Vidora is built on a foundation of the latest industry-standard technologies to ensure stability, speed, and security.

| Component | Technology | Version | Description |
| :--- | :--- | :--- | :--- |
| **Core Language** | Kotlin | `2.3.20` | Modern, safe, and interoperable. |
| **UI Framework** | Jetpack Compose | `2026.03.01` | Declarative UI for a fluid experience. |
| **Media Engine** | Media3 (ExoPlayer) | `1.10.0` | Industry-leading media playback. |
| **Image Loading** | Coil | `3.4.0` | Fast, hardware-accelerated image decoding. |
| **Database** | Room | `2.8.4` | Reliable, local data persistence. |
| **Data Storage** | DataStore | `1.2.1` | Modern, asynchronous preferences storage. |
| **Architecture** | MVVM | — | Clean separation of concerns and logic. |

---

## ✨ Key Features

### 🎬 Advanced Playback
*   **Gestures Control**: Intuitive swipe gestures for volume, brightness, and seeking.
*   **Volume Boost**: Amplify audio volume up to 200% for a true cinematic experience.
*   **Multi-Track Support**: Seamless switching between multiple audio tracks and subtitles.
*   **Subtitle Synchronization**: Real-time adjustment of subtitle delay and speed.
*   **Playback Speed**: Variable speed control from 0.25x to 4.0x.

### 📂 Smart Library
*   **Folder Organization**: Automatically groups videos by directory for easy browsing.
*   **Universal Search**: Instant search across all your local video content.
*   **History**: Keep track of your recently watched videos and resume where you left off.
*   **Exclude Folders**: Hide specific directories from your library for a cleaner view.

### 🎨 Premium Aesthetics
*   **Dynamic Accents**: Choose from a curated list of professional color themes.
*   **Pill-Shaped UI**: Modernized buttons and controls using premium pill-shaped designs.
*   **Glassmorphic UI**: Modern, translucent design elements that feel light and airy.
*   **Smooth Transitions**: Fluid animations when navigating between screens.

---

## 📸 Screenshots

<table align="center">
  <tr>
    <td align="center"><b>Home Screen</b></td>
    <td align="center"><b>Folders View</b></td>
    <td align="center"><b>All Videos</b></td>
    <td align="center"><b>Video Player</b></td>
  </tr>
  <tr>
    <td><img src="assets/Home-Screen.png" width="200" style="border-radius: 12px; border: 1px solid #ddd;"></td>
    <td><img src="assets/Home-Folder-Screen.png" width="200" style="border-radius: 12px; border: 1px solid #ddd;"></td>
    <td><img src="assets/Videos-Screen.png" width="200" style="border-radius: 12px; border: 1px solid #ddd;"></td>
    <td><img src="assets/Player-Screen.png" width="200" style="border-radius: 12px; border: 1px solid #ddd;"></td>
  </tr>
  <tr>
    <td align="center"><b>History</b></td>
    <td align="center"><b>Global Search</b></td>
    <td align="center"><b>Settings</b></td>
    <td align="center"><b>Quick Settings</b></td>
  </tr>
  <tr>
    <td><img src="assets/History-Screen.png" width="200" style="border-radius: 12px; border: 1px solid #ddd;"></td>
    <td><img src="assets/Search-Screen.png" width="200" style="border-radius: 12px; border: 1px solid #ddd;"></td>
    <td><img src="assets/Settings-Screen.png" width="200" style="border-radius: 12px; border: 1px solid #ddd;"></td>
    <td><img src="assets/Quick-Settings-Page.png" width="200" style="border-radius: 12px; border: 1px solid #ddd;"></td>
  </tr>
</table>

---

## 🚀 Installation & Requirements

### System Requirements
* **OS**: Android 8.0 (Oreo) or higher.
* **Architecture**: Supported on `arm64-v8a`, `armeabi-v7a`, `x86`, and `x86_64`.
* **Hardware**: Optimized for high-definition playback (4K/HDR supported).

### Permissions Explained
To provide a complete experience, Vidora requires:
* **Media Access**: To scan and play video files from your device.
* **All Files Access (Optional)**: Granting this allows for **Fast Delete**, bypassing the system "Allow/Deny" confirmation for a seamless workflow.
* **Notifications**: For media session controls and playback status.

### How to Install
1. Download the latest APK from the **[Releases](https://github.com/Maheswara660/Vidora/releases)**.
2. Enable "Install from Unknown Sources" in Android Settings.
3. Launch Vidora and grant the necessary permissions.

---

## 🏗️ Build from Source
Ensure you have **Android Studio Ladybug** and **JDK 17** configured.

```bash
# Clone the repository
git clone https://github.com/Maheswara660/Vidora.git

# Enter the project directory
cd Vidora

# Build the release variant
./gradlew assembleRelease
```

---

## ❤️ Support & Community
Vidora is a labor of love by a **solo developer**. Your support directly fuels the development of new features!

* ⭐ **Star**: Please give this project a star if you find it useful.
* ☕ **[Buy me a coffee](https://ko-fi.com/Maheswara660)**: Support my work via Ko-fi.
* 🤝 **Contribute**: Check out the [Contributing Guidelines](.github/CONTRIBUTING.md).

---

## 📜 License
Vidora is open-source software licensed under the **GNU General Public License v3.0**. See the [LICENSE](LICENSE) file for more information.

---

## ✉️ Message from Developer
> "Vidora was born out of a desire for a video player that feels professional yet looks beautiful. Every line of code and every UI component has been crafted to provide the best possible experience on Android. I hope Vidora becomes an essential part of your entertainment setup."
> — **Maheswara660**

<p align="center">
  <b>Vidora — Cinema in your pocket.</b><br>
  Made with ❤️ in India
</p>
