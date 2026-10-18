# Tasks

## 1. Module and dependency setup

- [ ] 1.1 Add `feature:scan-api`, `feature:scan`, `feature:details-api`, and `feature:details` to Gradle settings with convention-based build files; verify all projects resolve with `./gradlew projects`.
- [ ] 1.2 Add CameraX and bundled Latin/Cyrillic on-device text-recognition dependencies to the version catalog and required modules; verify dependency resolution and a debug compile with `./gradlew :feature:scan:compileDebugKotlin`.
- [ ] 1.3 Declare only `CAMERA` in the app manifest and keep all storage permissions absent; verify the merged debug manifest through `./gradlew :app:processDebugMainManifest`.

## 2. Scan session and Markdown export

- [ ] 2.1 Write focused failing tests in `common:data` for session creation, ordered section append, discard, missing-session lookup, timestamped Markdown content, and write failure; verify RED with `./gradlew :common:data:testDebugUnitTest`.
- [ ] 2.2 Add Android-free scan-session/export contracts and immutable models in `common:domain`, then implement the application-scoped in-memory session store and app-private Markdown exporter in `common:data`; verify the same tests are GREEN.
- [ ] 2.3 Add cancellation/error-path tests for the exporter and ensure no temporary image or text logging/persistence is introduced; verify with `./gradlew :common:data:testDebugUnitTest`.

## 3. Routes and navigation contracts

- [ ] 3.1 Write a failing `core:navigation` test for clearing child scan routes and returning to Home; verify RED with `./gradlew :core:navigation:testDebugUnitTest`.
- [ ] 3.2 Implement the focused navigator return-to-start operation and serializable scan/details route keys carrying only `sessionId`; verify the navigation test is GREEN.
- [ ] 3.3 Implement scan/details entry-provider contracts and register their modules in the app composition root; verify no implementation module depends on the other with `./gradlew :app:assembleDebug`.

## 4. Camera capture and on-device recognition

- [ ] 4.1 Write failing `feature:scan` ViewModel tests with fake recognizer/session contracts for manual recognition success, no-text retry, cancellation, and permission-denial effects; verify RED with `./gradlew :feature:scan:testDebugUnitTest`.
- [ ] 4.2 Implement the scan Route, Screen, immutable Orbit state/actions/effects, CameraX preview, visible frame, manual capture, frame-to-image crop, and bundled on-device Latin/Cyrillic recognition; verify the focused tests are GREEN.
- [ ] 4.3 Implement runtime camera-permission request/explanation/retry and release captured image data on success, failure, and cancellation; verify on a device or emulator that no text is auto-captured and denied permission remains recoverable.

## 5. Read-only scanning details

- [ ] 5.1 Write failing `feature:details` ViewModel tests for read-only ordered sections, Add scan, Back/discard, missing-session return-to-Home, successful export, and agreed write-failure discard; verify RED with `./gradlew :feature:details:testDebugUnitTest`.
- [ ] 5.2 Implement `feature:details` Route, Screen, ViewModel, session lookup, Add scan/Back/Finish side effects, and success/error feedback; verify the focused tests are GREEN.
- [ ] 5.3 Add default, Russian, and Uzbek Latin resources for every new scanning message/action and test or manually verify the required locales; verify `./gradlew :feature:scan:lintDebug :feature:details:lintDebug`.

## 6. Home and app integration

- [ ] 6.1 Write a failing `feature:home` presentation test for starting a new scan session and navigating to its stable route ID; verify RED with `./gradlew :feature:home:testDebugUnitTest`.
- [ ] 6.2 Add the localized Home Scan action and wire Home, scan, and details sections through the existing Navigation 3/Koin composition; verify the focused Home test is GREEN.
- [ ] 6.3 Verify end-to-end on a device or emulator: grant/deny/retry camera access, capture Russian/Uzbek Latin/English framed text, add a second section, return/discard, rotate mid-session, recover from absent restored session, save the timestamped private Markdown file, and observe the agreed write-failure behavior.

## 7. Approval and regression verification

- [ ] 7.1 Independently security-review camera permission, bundled recognizer dependencies, image disposal, app-private Markdown output, logging, and backup/retention assumptions; record findings and remediate all blocking issues.
- [ ] 7.2 Run focused module tests and `./gradlew :app:assembleDebug :app:lintDebug`; record exact GREEN results and any device-only verification limitations.
- [ ] 7.3 Independently code-review requirements, module boundaries, tests, navigation, localization, and error/cancellation behavior; resolve blockers before approval.
- [ ] 7.4 Run `openspec validate add-page-text-scanning --strict --no-interactive` and update roadmap/change evidence with approved verification outcomes.
