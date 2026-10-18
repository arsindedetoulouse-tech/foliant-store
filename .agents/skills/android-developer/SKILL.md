---
name: android-developer
description: Implement approved Kotlin, Android, Compose, Orbit and Koin changes in Foliant Book Store. Use with the QA skill for behavior-changing work and the architecture/security skills where applicable.
---

# Android Developer

## Inputs and boundaries

Load `AGENTS.md`, `SECURITY.md`, the active change and applicable design.
Load `qa-engineer` before behavior changes and `security-reviewer` for
security-sensitive work. Inspect the affected module, dependencies and tests.
Implement agreed behavior, not new product or architecture decisions.

## Implementation procedure

1. Map tasks to acceptance criteria and focused checks. Write and run a failing
   behavioral test first; follow the QA skill's RED -> GREEN -> refactor procedure.
   For documentation-only work use document checks, not artificial runtime tests.
2. Make the smallest complete change using existing patterns and helpers.
   Keep dependent call sites consistent; avoid unrelated fixes/refactoring.
3. Preserve layer boundaries and type safety. Do not leak DTOs/entities/Ktor
   types into domain or presentation. Map infrastructure failures at the data
   boundary into the existing project/domain error model if one exists.
   If a necessary contract is missing, resolve its design instead of claiming
   `ApiErrorResponse` or another imaginary abstraction already exists.
4. Preserve cancellation. Do not turn `CancellationException` into an ordinary
   failure through `catch`, `runCatching` or custom result wrappers. Surface
   failures using established logging/UI mechanisms, without leaking secrets;
   do not invent success-shaped fallback values.
5. Run UI work in Orbit intents or the appropriate ViewModel scope. Sources own
   blocking-I/O dispatcher switches. Inject dispatchers when existing patterns
   or deterministic tests require them. Do not use `GlobalScope`, unmanaged
   long-lived scopes or redundant dispatchers for already asynchronous calls.

## Compose, Orbit and navigation

- Routes obtain ViewModels, collect state/side effects lifecycle-safely and wire
  navigation/UI operations. Keep rendering minimal.
- Screens receive immutable state and callbacks, preferably
  `FeatureScreen(state, onAction)`; keep them stateless/previewable where practical.
  Do not pass ViewModels deeply or resolve application dependencies in reusable UI.
- Separate persistent state, accepted actions and one-time side effects.
  Navigation, snackbars/toasts and external intents belong in side effects, not
  persistent completion flags acted on during recomposition.
- Use `collectAsStateWithLifecycle()` for Flow/StateFlow or an established
  lifecycle-aware abstraction. Do not assume Orbit collectors are lifecycle-aware
  without checking their API/configuration.
- Do not perform navigation, network/file/persistence work, external intents,
  analytics or application-state mutation during normal composition.
  Use ViewModel actions, Orbit effects or appropriate Compose effect APIs.
- Keep destination declarations and navigation actions in separate extensions
  where the surrounding feature follows that pattern. Use Navigation 3's current
  abstractions, not an introduced `NavController` dependency.
- Reuse `core:designsystem` components/tokens/theme. Draw a theme-consistent root
  surface when needed; assess light/dark contrast, insets and accessibility.
- Prefer `val` UI state and immutable values. Do not expose MutableStateFlow,
  writable repository state or mutable collections to UI callers.

## DI and resources

- Keep Koin modules near their owner and register them in
  `app/src/main/kotlin/com/arsinde/foliantstore/App.kt`.
- Create objects depending on navigation/UI callbacks at the Route boundary,
  not Koin definitions with runtime function parameters.
- Use explicit `parametersOf(...)` only for legitimate construction dependencies
  such as stable IDs/configuration. Test every parameterized resolution.
- Do not retain Activity, Fragment, navigation or Compose objects in long-lived
  dependencies. Use application Context only for necessary platform access.
- Put user-facing text in resources. Current locales are `ru-RU` and
  `uz-Latn-UZ`, with application resources under `values-b+ru+RU` and
  `values-b+uz+Latn+UZ`. Add feature translations in matching qualifiers and
  maintain default resources. Existing Home translations are incomplete.
- Keep debug-only values, endpoint constants and machine-readable identifiers in
  code/configuration unless Android or an established convention requires resources.

## Networking and API changes

Reuse the centralized client; do not create feature-local clients without an
approved architectural reason. Keep endpoints, DTOs, serialization and transport
details in data/network. Do not branch on raw HTTP/Ktor/serialization exceptions
in presentation.

For a changed API: update DTO/annotations, inbound/outbound mappers, source and
repository, affected domain contracts and presentation, then focused serialization
and mapper tests. Verify nullable/optional/renamed/removed fields all the way to UI
state; do not silently discard semantic changes.

## Gradle and completion

Use convention plugins and `gradle/libs.versions.toml`, type-safe project accessors
where established, and existing dependency ordering. Do not duplicate dependencies
already provided by a convention plugin. Keep toolchain/SDK settings aligned with
their authoritative convention configuration; do not enable warnings-as-errors
locally or change unrelated dependency versions.

Run focused checks from the QA skill and inspect the resulting change, including
new/untracked files. Update directly affected documentation/resources/contracts.

## Output / handoff

Hand the exact scope and evidence to an independent reviewer; do not self-approve
or claim unexecuted checks passed.
