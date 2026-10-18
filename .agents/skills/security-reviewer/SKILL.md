---
name: security-reviewer
description: Assess Foliant security-sensitive requirements, design and implementation against SECURITY.md. Use for auth, protected data, storage, network, Android exposure and release-security changes.
---

# Security Reviewer

## Authority and inputs

`SECURITY.md` is the security authority; this skill defines review procedure,
not exceptions. Load it with `AGENTS.md`, the active change and relevant
requirements/design. Security-sensitive implementation loads this skill before
work; final security approval requires an independent reviewer.

## Procedure

1. Bound the current attack surface: entry points, trust boundaries, data classes,
   privileges, dependencies and deployment assumptions. Separate current behavior
   from proposed backend/auth/storage features. Foliant is not a kiosk/DPC.
2. Inspect actual source/configuration, including relevant new/untracked files.
   Never conclude an untracked skeleton is safe from an empty diff.
   Do not read secrets/local credentials when metadata or placeholders suffice.
3. Assess affected authentication/authorization and session transitions; do not
   confuse hidden UI with access control. Keep authentication material outside
   presentation unless an explicit reviewed requirement needs it.
4. Assess protected data minimization, storage, retention/deletion and Android
   backup/device transfer. Preferences DataStore is configuration storage, not
   approved secret protection. Require a design before protected data is added.
5. Assess network endpoints, TLS/hostname validation and logging/redaction.
   Centralized client construction alone does not prove endpoint/auth security.
6. Assess permissions, exported components, intents/deep links, providers, WebView
   and external input validation only where present/affected. Intentional launcher
   exposure is not automatically a vulnerability.
7. Assess release flags, R8 rules, signing inputs, repository trust and relevant
   dependency/wrapper integrity. Shrinking is not a substitute for authorization
   or secret protection. Do not claim CVE/provenance verification without evidence.
8. Verify mitigations with targeted tests/configuration checks. Do not exploit
   live systems, upload private code, print secrets or weaken controls to reproduce
   a finding. Follow the private-reporting rules in `SECURITY.md`.
9. Report concrete vulnerabilities separately from defense-in-depth gaps,
   unimplemented future controls and owner decisions. Use severity/confidence
   grounded in exploit prerequisites and actual impact.
10. Confirm remediations and residual risks independently before approval.
    Unresolved critical/high findings block approval; no role can silently accept
    them or authorize a policy violation.

## Output / handoff

Record affected files/components, attack surface/data, controls checked, findings
with exact file/line evidence, severity, confidence, impact/prerequisites,
mitigation, residual risks and approval/block status. Explicitly identify skipped
checks and unresolved owner decisions. "No confirmed vulnerability" is not proof
of release readiness, dependency safety or untested device behavior.
