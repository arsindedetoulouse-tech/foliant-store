# Proposal

## Why

The Android skeleton is Foliant Book Store, but its engineering contract describes
another product and requires seven role skills that do not exist. Development needs
an actionable SDD contract grounded in the current code rather than assumed infrastructure.

## What Changes

- Keep mandatory, cross-cutting constraints and skill selection in a concise `AGENTS.md`.
- Extract procedures into seven role skills without changing generated OpenSpec skills.
- Align `SECURITY.md` with an ordinary Android application, preserving security controls.
- Populate OpenSpec context with verified architecture, scope and review gates.
- Record baseline review findings and validation limits without changing application code.
- Record the owner's requested follow-up plan in ROADMAP.md with agent entry
  points and a maintenance protocol, without starting the planned work.

## Capabilities

### New Capabilities

None. This is engineering documentation, not new product behavior.

### Modified Capabilities

None. `skip_specs: true` explicitly excludes product delta specs.

## Impact

Only engineering policies, role skills, OpenSpec configuration and this change.
No Kotlin, Gradle, manifest, resource, dependency, generated skill or local configuration changes.

## Agreed Scope

The owner confirmed that Foliant Book Store is not a kiosk/DPC application.
Keep all seven roles. Use OpenSpec for non-trivial changes; a genuinely small,
behavior-preserving edit may proceed without a change if its exemption is explained.
Application fixes require separate changes. No commits or archival are requested.

## Acceptance Criteria

- AC1: Active policies identify Foliant correctly and distinguish implemented infrastructure from plans.
- AC2: All seven roles have discoverable skills with inputs, procedure, boundaries and evidence.
- AC3: Global invariants remain mandatory regardless of which skills are loaded.
- AC4: SDD defines approval, TDD, independent review and honest completion evidence.
- AC5: Security controls are not weakened; missing product security decisions are explicit.
- AC6: Documentation is validated and independently reviewed; application files remain unchanged.
- AC7: ROADMAP.md records the five proposed steps, factual status, dependencies,
  completion evidence and update rules; agents discover it through AGENTS and
  OpenSpec context without treating entries as implementation approval.
