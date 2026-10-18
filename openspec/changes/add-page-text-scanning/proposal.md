# Proposal: Add page text scanning

## Why

Users need to turn the text visible on one or more book pages into one local
Markdown document without manually retyping it. The first product slice must
provide a complete on-device capture-to-file flow while keeping scanned content
off the network.

## What Changes

- Add a Scan action to Home that opens a camera flow after handling the Android
  camera permission.
- Let a user manually capture text inside a visible camera frame and recognize
  Russian, Uzbek Latin, and English text entirely on device.
- Add a `feature:details` module that owns the read-only scanning-details screen
  and displays the recognized, accumulated text. The flow supports adding
  additional scans as separate Markdown sections and discards an unfinished
  session when the user navigates back to Home.
- Save completed text as a timestamped Markdown file in app-private storage.
  If saving fails, show an error and discard the session as agreed.
- Discard captured images after recognition; do not upload images or recognized
  text, retain an account, or add a remote backend in this slice.
- Localize all new user-visible scanning text for Russian and Uzbek Latin.

## Capabilities

### New Capabilities

- `page-text-scanning`: Lets a user capture framed page text, accumulate
  read-only on-device OCR results, and save the result as local Markdown.

### Modified Capabilities

None.

## Impact

- Home receives an entry action; a new scan feature contributes the camera
  destination, while `feature:details` contributes the scanning-details
  destination to Navigation 3. The architecture phase defines their public
  contract and navigation ownership.
- The Android app manifest requires the `CAMERA` runtime permission.
- New CameraX and on-device text-recognition dependencies are required; their
  versions and Android compatibility must be selected during design.
- App-private file I/O stores the final Markdown output. No network contract,
  account, external storage permission, or remote OCR is introduced.
- Camera permission, arbitrary captured text, local persistence, and dependency
  provenance require security assessment before implementation approval.
