# Design

## Context

R1 established Java 21 as the Gradle daemon requirement and verified local unit
tests, debug assembly and lint. GitHub Actions is the owner-selected CI platform.

## Decisions

1. Trigger on push and pull request, with `contents: read` permissions only.
2. Use Ubuntu runner, Java 21, Android SDK platform 37 and official setup actions.
3. Run one Gradle command covering current unit-test modules, debug assembly and
   lint. Cache Gradle through the official Gradle action.
4. Install exact OpenSpec CLI `1.13.2` through `npx` and run strict validation.
5. Do not claim a successful CI run until the committed workflow executes on
   GitHub. Local validation checks YAML/text and continues to use Gradle directly.

## Risks / Trade-offs

- [Action tags can change] -> Use maintained official actions and minimal
  permissions; SHA pinning can be added with an approved dependency policy.
- [Runner lacks Android 37] -> Install the platform explicitly.
- [External npm/download failure] -> CI reports infrastructure failure rather
  than masking it as a passing build.

## Verification Evidence

The workflow structure confirms required gates, read-only permissions and no
`pull_request_target` or expression interpolation. The CI-equivalent local command
passed:

```sh
./gradlew :common:data:testDebugUnitTest :feature:home:testDebugUnitTest \
  :core:navigation:testDebugUnitTest :app:assembleDebug :app:lintDebug \
  --console=plain --quiet
npx --yes @fission-ai/openspec@1.13.2 validate --all --strict --no-interactive
```

All five current OpenSpec changes passed strict validation. Independent security
review found no confirmed vulnerability. Residual supply-chain risk remains from
version-tagged third-party action references and the external npm package; the
workflow has no secrets, uses exact OpenSpec package version and grants only
`contents: read`.

This repository has no configured remote, so no GitHub Actions run exists yet.
Remote successful execution is required before R4 can be removed from the roadmap.

Independent code review found no blockers or major issue in workflow ordering,
triggering or recorded status. The remote-run limitation remains unchanged.
