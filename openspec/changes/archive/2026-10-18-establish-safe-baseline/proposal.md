# Proposal

## Why

The project has no initial versioned baseline: all project sources and engineering
artifacts are untracked. A reviewed baseline is required before later changes can
be reliably compared, reviewed or reverted.

## What Changes

- Inventory the untracked repository content without reading local credentials.
- Tighten ignore rules for generated, local and signing-related material.
- Validate that the intended baseline contains source, configuration, policies,
  skills and OpenSpec artifacts but excludes local secrets and outputs.
- Create the initial commit only after explicit owner approval.

## Capabilities

### New Capabilities

None. This is repository hygiene and version-control setup, not product behavior.

### Modified Capabilities

None. `skip_specs: true` explicitly excludes product delta specs.

## Impact

`.gitignore`, this change and R2 roadmap status. The initial commit, if approved,
will include the reviewed baseline. No runtime behavior, dependencies or product
contracts change.

## Acceptance Criteria

- AC1: The baseline inventory is classified without exposing local secret contents.
- AC2: Ignore rules cover relevant generated/local/signing artifacts without
  hiding source or required public Android configuration.
- AC3: Security review reports no unaddressed Critical or High baseline issue.
- AC4: The intended commit scope is listed and verified before staging.
- AC5: No commit is created without explicit owner approval.
