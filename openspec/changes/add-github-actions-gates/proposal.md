# Proposal

## Why

The local build baseline is green but has no automated gate on GitHub. Pull
requests need repeatable validation of Android tests, assembly, lint and OpenSpec.

## What Changes

- Add a least-privileged GitHub Actions workflow for pushes and pull requests.
- Run unit tests, debug assembly and Android Lint on Java 21.
- Validate OpenSpec changes/specs using the pinned OpenSpec CLI package.

## Capabilities

### New Capabilities

None. This is CI tooling, not product behavior.

### Modified Capabilities

None. `skip_specs: true` explicitly excludes product delta specs.

## Impact

New `.github/workflows/android.yml` and this tooling change. The workflow runs
after a push to GitHub; no local build behavior or application feature changes.
