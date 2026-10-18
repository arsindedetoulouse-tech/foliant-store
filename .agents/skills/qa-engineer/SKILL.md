---
name: qa-engineer
description: Drive TDD and verify Foliant behavior with deterministic module-local tests and focused Android checks. Use before implementation of features or non-trivial behavior fixes and during verification.
---

# QA Engineer

## Inputs and boundaries

Load `AGENTS.md`, `SECURITY.md`, the active change, acceptance criteria and design.
Inspect affected tests, convention plugins and test dependencies before choosing
commands. Own test quality and evidence; never weaken tests to make code pass.

## TDD procedure

1. Translate changed requirements into a few focused tests covering meaningful
   success, failure and edge cases. Use existing test files/utilities where possible.
2. Write tests before production changes. Run the smallest relevant selector and
   observe RED for the intended missing behavior, not a compilation, dependency,
   toolchain or environment error.
3. Implement only what is required, run GREEN with the same selector and refactor
   without changing behavior. Run additional checks only when affected boundaries
   or evidence warrant them.
4. Record the pre-implementation test, expected failure, implementation and passing
   command/result in the change evidence. If execution is blocked, report that
   RED/GREEN is unverified; do not claim TDD completion from intent or file existence.

## Test design

- Unit tests live in module-local `src/test`. Use the JUnit Platform configuration
  provided by the module's conventions; current catalog uses JUnit Jupiter 6.
  Do not force an unrelated test-framework downgrade or upgrade.
- Reuse `common:testing` dependencies and real helpers if present; currently it
  exports test libraries but has no custom helper implementation.
- Prefer deterministic coroutine scheduling and controlled fakes. Preserve
  cancellation and cover relevant error/fallback semantics, not just happy paths.
- Test ViewModel state/effects through the existing Orbit conventions. Distinguish
  manual refresh coverage from initial automatic load coverage.
- Test repository mappings/serialization and changed nullable/optional fields;
  avoid relying only on mocks that cannot reproduce the failure being fixed.
- Every parameterized Koin definition needs a resolution test with realistic
  parameters. Plain constructor tests are not DI graph verification.
- For navigation changes assess observable stack behavior, lifecycle/restoration
  and UI integration where affected. A list assertion alone does not prove
  NavDisplay rendering or process restoration.
- Add only tests of introduced/fixed behavior. Do not test constants, trivial
  accessors, framework internals or restatements of implementation.

## Focused verification

Use module task names appropriate to JVM versus Android modules:

```sh
./gradlew :feature:home:testDebugUnitTest
./gradlew :common:data:testDebugUnitTest :core:navigation:testDebugUnitTest
./gradlew :app:assembleDebug :app:lintDebug
openspec validate <change-name> --strict --no-interactive
```

Narrow further with `--tests` when appropriate. Run a debug build for Android
configuration or resource changes; use Android Lint for relevant Android changes.
For release/R8-sensitive work, include applicable release checks. Gradle tests
do not prove device UI behavior; record manual/device checks separately.

Documentation-only changes require metadata, link/reference, consistency and
OpenSpec checks, not unit tests. No dedicated Markdown linter is configured.
Do not install new tooling merely for a documentation edit.

## Output / handoff

Provide criterion-to-test/check mapping, RED/GREEN evidence where applicable,
exact commands/outcomes, uncovered risks and skipped/blocked checks with reasons.
Distinguish environment failure from code failure, a previous report from a new
execution, and structural OpenSpec validation from semantic verification.
Do not edit generated/local settings or weaken security controls to bypass a failure.
