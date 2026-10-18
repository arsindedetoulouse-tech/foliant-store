# Design

## Context

The root build sets Java/Kotlin compilation toolchains to 21. The committed
`gradle/gradle-daemon-jvm.properties` instead requires Java 25 and provisions
Adoptium Java 25 through Foojay. The launcher runtime is Debian Java
`25.0.5-ea`; Gradle does not accept it as the requested provisioning target and
the configured download cannot reach the linked release. The machine already has
an Adoptium Java 21 installation in Gradle's managed JDK cache.

The root catalog declares AGP `9.4.1`; included `build-logic` currently declares
AGP `9.4.0` directly. Both root projects use the same plugin repositories.

## Goals / Non-Goals

**Goals:**

- Remove the unnecessary daemon Java 25 dependency.
- Use the project compilation Java version as the daemon requirement.
- Remove AGP version drift and establish a repeatable green local baseline.
- Record honest test/build/lint evidence.

**Non-Goals:**

- Change compiler/Android SDK targets, Gradle wrapper, Kotlin version or runtime
  dependencies.
- Add CI, dependency verification, checksums, a JDK installer or product tests.
- Fix Home behavior, introduce product capabilities or claim device testing.

## Decisions

1. **Use Java 21 for the Gradle daemon.** It matches the declared compile
   toolchain and a managed Adoptium JDK is already available. Java 25 was a
   daemon-only requirement and is neither needed by project source nor reliably
   provisioned in this environment. This avoids accepting an EA Debian runtime
   or changing compilation targets just to satisfy the daemon.
2. **Keep a local-JDK daemon requirement without stale download URLs.** Gradle's
   `updateDaemonJvm --jvm-version=21` cannot generate URLs because this project
   does not configure a toolchain download repository. The existing URLs resolve
   Java 25 and must not remain after selecting Java 21. Commit only the daemon
   version with a clear local-JDK requirement; do not add a resolver plugin or
   guess Foojay URLs as part of a baseline restoration.
3. **Use catalog AGP 9.4.1 in build logic.** The included build's direct plugin
   dependency must equal the root catalog version. This is alignment, not an
   unrelated upgrade: root aliases already resolve 9.4.1.
4. **No TDD test is applicable.** The change has no product behavior. The
   acceptance tests are Gradle configuration, existing unit tests, debug
   assembly and Android Lint. A failure before task execution is valid
   configuration evidence, not RED for a runtime test.

## Risks / Trade-offs

- [A clean machine lacks Java 21] -> Gradle reports the required daemon version;
  install a supported Java 21 runtime. Toolchain auto-provisioning is intentionally
  not claimed because no resolver repository is configured.
- [JDK 21 may not satisfy a hidden build-tool requirement] -> Run the full
  baseline command after regeneration; retain observed task failure if it occurs.
- [Plugin alignment exposes AGP compatibility issues] -> Configure and run the
  same baseline command; do not upgrade unrelated dependencies as a workaround.
- [Generated daemon URLs change] -> Inspect the regenerated file and preserve
  all supported platform mappings rather than editing one host-specific URL.
- [Network dependency retrieval fails] -> Distinguish repository/network failure
  from source/build failure and record the exact blocker.

## Migration Plan

1. Ensure Gradle can run with managed Java 21 for the daemon.
2. Regenerate the daemon JVM file for 21 and align build-logic AGP.
3. Run focused existing tests plus assembly and lint.
4. Update R1 status/evidence. Revert the two configuration files to restore the
   prior behavior if validation identifies an incompatibility.

## Implementation and Verification Evidence

- The local Java metadata confirms a managed Eclipse Adoptium `21.0.7` LTS JDK
  at `~/.gradle/jdks/eclipse_adoptium-21-amd64-linux.2`. The local system Java 25
  is an EA Debian runtime, while the previous daemon policy required a provisioned
  Java 25 distribution.
- `./gradlew updateDaemonJvm --jvm-version=21 --console=plain --quiet` failed
  because no toolchain download repository is configured. This confirms that the
  old Java 25 URLs could not be safely regenerated as Java 21 URLs. The committed
  daemon policy now declares local Java 21 and removes those stale mappings.
- `./gradlew --version --console=plain` reports
  `Daemon JVM: Compatible with Java 21, any vendor, nativeImageCapable=false`.
- The root catalog and included build both declare AGP `9.4.1`.
- The following completed successfully with the final configuration:

  ```sh
  ./gradlew :common:data:testDebugUnitTest :feature:home:testDebugUnitTest \
    :core:navigation:testDebugUnitTest :app:assembleDebug :app:lintDebug \
    --console=plain --quiet
  ```

  Test XML reports for common data, core navigation and feature Home contain no
  `<failure>` or `<error>` elements. `app/build/reports/lint-results-debug.xml`
  has zero Error-severity issues, and `app/build/outputs/apk/debug/app-debug.apk`
  was produced.

No new behavior was implemented, so TDD RED/GREEN is not applicable. These
commands verify build configuration and existing behavior; they do not prove
device behavior, clean-machine Java installation, CI availability or release
assembly.

## Independent Review

Independent review found the implementation minimal and the local-JDK policy
honest: Java 21 selection and AGP `9.4.1` alignment introduce no resolver or
unrelated version change. It identified one Medium documentation defect: the
roadmap still called R1 blocked after the successful baseline. R1 was removed
from the rolling roadmap after all acceptance criteria, validation and review
were complete; R2 is now the next planned outcome.

The reviewer did not rerun Gradle. Build outputs are ignored and so were not
independently inspected; the recorded command exit status and generated report
checks are the available verification evidence. Clean-machine provisioning, CI,
device behavior and release assembly remain unverified.
