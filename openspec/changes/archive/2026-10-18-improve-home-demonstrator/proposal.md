# Proposal

## Why

The Home demonstrator loses fresh data when only its cache write fails, has an
incomplete dark surface and remains English in supported Russian and Uzbek locales.

## What Changes

- Keep successfully loaded Home data when saving it locally fails.
- Render Home on a Material theme surface in light and dark modes.
- Localize Home user-visible text for Russian and Uzbek Latin.

## Capabilities

### New Capabilities

- `home-demonstrator-resilience`: Reliable fresh-data behavior and consistent,
  localized presentation for the Home demonstrator.

### Modified Capabilities

None.

## Impact

Common Home data implementation/tests, Home Compose screen/resources and one new
OpenSpec capability. No backend, authentication, persistence schema or public API
change.
