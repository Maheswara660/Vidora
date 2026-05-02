# Changelog

All notable changes to this project will be documented in this file.

## [1.0.0] - 2026-04-29
### Added
- Initial commit

## [1.1.0] - 2026-05-02
### Added
- **Fast Delete**: New option to bypass system confirmation dialogs on Android 11+ by granting "All Files Access" permission.
- **Volume Boost**: Loudness enhancement support up to 200% (400% gain) during video playback.
- **Dynamic Preference Updates**: Settings like Volume Boost and Autoplay now take effect immediately during playback without requiring a restart.

### Changed
- **UI Modernization**: Standardized all bottom sheet action buttons to use a consistent, full-width pill-shaped design.
- **Enhanced Dialogs**: Redesigned all preference sheets (Theme, Accent, Subtitles, etc.) for better visual hierarchy and readability.
- **Button Aesthetics**: Transitioned from box-shaped to modern pill-shaped buttons throughout the app.

### Fixed
- **Volume Boost Toggle**: Fixed an issue where the toggle was not interactive in the Audio Settings.
- **ProGuard Stability**: Updated ProGuard rules to ensure background services and permission launchers work correctly in release builds.
- **UI Responsiveness**: Fixed click propagation issues in several preference components.
