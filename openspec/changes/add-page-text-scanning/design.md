# Design

## Context

See `proposal.md` for product motivation and
`specs/page-text-scanning/spec.md` for behavior. The application currently has
only the Home destination. Home owns a demonstration data flow; it neither has
camera access nor a scan session. Navigation 3 restores routes across process
death, while the first slice intentionally restores an unfinished scan session
only across configuration changes.

The current module graph has no scanner, text-recognition dependency, camera
permission, or file-export contract. The existing Home feature exposes its
destination through `feature:home-api`, and `MainActivity` composes feature
entry providers.

## Goals / Non-Goals

**Goals:**

- Add a CameraX-backed, manually triggered framed capture flow and entirely
  on-device recognition for the agreed scripts.
- Create `feature:details` as the owner of the read-only accumulated-text UI.
- Keep full recognized text out of navigation arguments while preserving an
  unfinished session through configuration changes.
- Export only the final Markdown file to app-private storage.

**Non-Goals:**

- Remote OCR, authentication, a backend, sync, external storage, sharing,
  editing recognized text, or retaining page images.
- Restoring unfinished sessions after process death.
- Adding a history, reader, or delete UI for saved files.

## Decisions

### Module and navigation boundaries

- Add `feature:scan-api` and `feature:scan` for the camera destination and
  capture flow, plus `feature:details-api` and `feature:details` for the
  scanning-details destination required by the proposal. The API modules expose
  only serializable route keys; the implementation modules contribute their
  entry providers.
- Add both implementation modules to the app composition root and both API
  modules only where their routes are needed. Home navigates to the scan route;
  scan navigates to details; details returns to the existing scan route to add a
  section. Implementation modules never depend on each other.
- Route arguments carry only a generated `sessionId`. A shared full-text object
  is not a navigation argument, preventing oversized saved back stacks and text
  duplication.
- Extend the core navigator with a focused return-to-start operation. Details
  uses it after Back or an unavailable restored session, so all scan routes are
  removed instead of returning to a camera route with the same missing session.

Alternatives considered: placing details inside `feature:scan` conflicts with
the owner-requested `feature:details` boundary. Passing accumulated text through
route arguments conflicts with the project's stable-small-argument rule.

### Session, domain, and export ownership

- `common:domain` defines immutable scan-section/session models and contracts
  for session operations and Markdown export. The contracts are Android-free.
- `common:data` provides an application-scoped in-memory session store and an
  app-private Markdown exporter. The store preserves state across ViewModel
  recreation/configuration change, but contains no disk-backed temporary state;
  it is deliberately empty after process death.
- The exporter creates a timestamped `.md` filename in app-private files
  storage and writes numbered section headings with their recognized text. It
  reports failures explicitly; it does not silently create empty output.
- Details resolves the session by ID. If it is absent, its Route handles the
  one-time unavailable-session effect by displaying the localized message and
  returning to Home.

Alternatives considered: DataStore and a database would persist unfinished text
beyond the agreed lifecycle and require a separate retention/storage decision.
External storage would require a different sharing/permission contract.

### Camera and on-device OCR boundary

- `feature:scan` owns the CameraX preview and the visual frame. A manual capture
  is cropped to the frame's image coordinates before recognition.
- Its ViewModel depends on an Android-free recognition contract. An adapter in
  the scan data layer uses bundled on-device recognizers for Latin and Cyrillic
  scripts, covering Uzbek Latin/English and Russian respectively. Dependency
  versions are added through the version catalog after compatibility review.
- Captured image bytes are held only for the capture/recognition operation and
  released on completion, cancellation, or failure. Recognition runs off the
  main thread and preserves coroutine cancellation.
- `CAMERA` is declared in the manifest and requested only after the Home Scan
  action. A denied request produces a presentation effect for the localized
  explanation/retry flow.

Alternatives considered: cloud OCR would violate the agreed on-device boundary.
Continuous analysis would contradict the agreed manual capture interaction.

### Presentation state and error ownership

- Scan and details follow the existing Route -> Orbit ViewModel -> Screen
  structure. Screens receive immutable state and action callbacks; Routes
  collect one-time navigation/message effects with lifecycle awareness.
- Recognition success appends a `ScanSection` to the session before navigating
  to details. Empty OCR results keep the user in the camera flow with a retry
  message. Permission denial, absent session, and export result are modeled as
  one-time effects rather than persistent navigation state.
- All new text is supplied through default, Russian, and Uzbek Latin resources.
  Recognized page content is data, not a localized resource.

### Security controls

- The only new permission is runtime `CAMERA`; no broad storage permission,
  exported component, deep link, network upload, credential, or analytics
  integration is introduced.
- Captured images are not logged, cached, exported, or retained. Recognized
  text remains on device and only the owner-requested final file is written to
  app-private storage.
- The current slice declares no protected-data workflow. If a future scope
  permits protected or personal scanned content, storage, backup/device
  transfer, retention, deletion, and user access must be explicitly designed
  and independently security-reviewed before implementation.
- Dependency provenance, bundled-recognizer footprint, and runtime permission
  handling require security review before final approval.

## Risks / Trade-offs

- [On-device OCR quality varies with lighting, focus, and page layout] ->
  manual retry and the no-text result remain part of the flow; no false-success
  fallback is added.
- [The visual frame and captured image can use different coordinate systems] ->
  use CameraX transformation metadata and test frame-to-image cropping.
- [In-memory sessions are lost after process death] -> detect missing session,
  display the agreed message, and return to Home.
- [A write failure discards text by agreed product behavior] -> make the
  destructive result explicit in the localized error and cover it with tests.
- [Bundled OCR increases application size] -> use only the required Latin and
  Cyrillic recognizers and assess release-size impact before approval.

## Migration Plan

1. Add modules, catalog dependencies, runtime permission, routes, and Koin
   registrations without changing existing Home loading behavior.
2. Implement contracts, session/export data implementations, scan capture, and
   details UI through TDD.
3. Verify permission, crop, OCR-result, navigation, export, localization, and
   process-death recovery paths; complete security and independent code review.
4. Roll back by removing the new routes/modules and `CAMERA` permission. No
   schema, remote API, or persistent unfinished-session migration exists.
