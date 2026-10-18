# Tasks

## 1. Build configuration

- [x] 1.1 Confirm the daemon Java 25 selection failure and the managed Java 21 availability; record the evidence in design.md.
- [x] 1.2 Set the Gradle daemon JVM requirement to Java 21 and remove stale Java 25 download URLs; verify Gradle selects the local managed JDK without claiming unsupported auto-provisioning.
- [x] 1.3 Align build-logic AGP with the root catalog version and verify the resolved declarations match.

## 2. Baseline verification

- [x] 2.1 Run existing `common:data`, `feature:home` and `core:navigation` unit tests; record the exact result.
- [x] 2.2 Run `:app:assembleDebug` and `:app:lintDebug`; record the exact result and distinguish environment failure from build failure.
- [x] 2.3 Update ROADMAP.md with the active/completed/block status and evidence link; remove R1 only if all acceptance criteria are met.

## 3. Review

- [x] 3.1 Validate the OpenSpec change, inspect the configuration diff and independently review the configuration and verification evidence.
