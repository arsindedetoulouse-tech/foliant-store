# Design

## Context

`HomeRepositoryImpl` wraps remote load and cache write in the same `runCatching`.
As a result, a save failure triggers the remote-load fallback. Home uses a root
`Column` without a theme surface, and Home strings exist only in default resources.

## Goals / Non-Goals

**Goals:** Preserve fresh data after cache-write failure, supply a theme surface,
and translate Home strings in declared app locales.

**Non-Goals:** Change remote/cache source contracts, add a logging dependency,
change cached-data fallback after remote failure, introduce new screens or add
backend/offline requirements.

## Decisions

1. Load remote data before attempting the cache write. Cache-write failure is
   intentionally non-fatal and does not invoke local fallback; remote-load failure
   continues to use the existing local fallback.
2. Use Material `Surface` as Home's root, with `fillMaxSize`, and place the
   existing column inside it. This provides theme background/content colors without
   changing screen state or navigation ownership.
3. Add matching Home resources under `values-b+ru+RU` and `values-b+uz+Latn+UZ`.
   Keep default resources as the fallback.
4. Add the repository regression test before implementation. Compose visual
   behavior is verified through debug build/lint and manual inspection because the
   module has no established Compose UI test harness.

## Risks / Trade-offs

- [Cache failure becomes unobservable] -> Do not fabricate logging infrastructure;
  keep the non-fatal behavior explicit and introduce diagnostics only with a
  dedicated requirement.
- [Translation quality changes product wording] -> Keep concise equivalent text;
  owner review can refine copy separately.
- [Theme fix is not visually tested on device] -> Record build/lint evidence and
  leave device validation explicitly unverified.

## TDD and Verification Evidence

The focused cache-write regression test was added before the repository change:

```sh
./gradlew :common:data:testDebugUnitTest --tests \
  'com.arsinde.foliantstore.data.home.HomeRepositoryTest.repository returns fresh data when caching fails' \
  --console=plain --quiet
```

It failed as expected on the original implementation because `disk full` escaped
from `saveLocal`. After separating the failure paths, the following passed:

```sh
./gradlew :common:data:testDebugUnitTest :feature:home:testDebugUnitTest \
  :app:assembleDebug :app:lintDebug --console=plain --quiet
```

The command completed successfully and generated debug lint reports. This verifies
resource compilation and static checks, but no emulator/device visual inspection
of the dark theme or locale selection was run.

## Independent Review

Independent review found that the initial Surface refactor removed
`fillMaxSize()` from the nested column, which would have moved Home content from
centered to the top of the screen. The column modifier was restored, preserving
both the theme surface and prior centered layout. Focused tests, debug build,
lint and strict OpenSpec validation passed again after the correction.

No device/emulator visual test was performed. The completed change is recorded
in OpenSpec and Git history; R3 has been removed from the rolling roadmap.
