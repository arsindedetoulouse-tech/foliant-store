# Proposal

## Why

The Android baseline cannot configure because the committed daemon JVM requirement
selects an unavailable Java 25 distribution, despite the project compiling for
JVM 21 and having a compatible cached JDK 21. Development needs a reproducible
local baseline before application work or CI can be trusted.

## What Changes

- Align the committed Gradle daemon JVM requirement with the project JVM 21
  compilation target and the available supported JDK.
- Align the Android Gradle Plugin version used by included build logic with the
  version catalog.
- Verify focused unit tests, debug assembly and Android Lint using the revised
  configuration.
- Document exact evidence and any remaining external-environment limits.

## Capabilities

### New Capabilities

None. This is build tooling and introduces no product behavior.

### Modified Capabilities

None. `skip_specs: true` explicitly excludes product delta specs.

## Impact

`gradle/gradle-daemon-jvm.properties`, `build-logic/build.gradle.kts`, this
change and the active roadmap state. No Kotlin, manifest, resource, production
dependency or user-visible behavior change is intended.

## Acceptance Criteria

- AC1: A local supported Java 21 installation satisfies the committed daemon
  requirement without downloading Java 25.
- AC2: Root plugin aliases and included build logic use the same AGP version.
- AC3: Existing unit tests, `:app:assembleDebug` and `:app:lintDebug` run to
  completion with the revised configuration.
- AC4: The change records exact commands/results and remaining limitations.
- AC5: R1 is marked accurately in `ROADMAP.md`; no unrelated roadmap outcome
  or product behavior is changed.
