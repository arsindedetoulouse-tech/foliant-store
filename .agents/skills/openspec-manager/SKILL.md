---
name: openspec-manager
description: Manage Foliant Book Store SDD scope, approvals, artifact handoffs and verification. Use for non-trivial changes and lifecycle decisions; use existing openspec operation skills for CLI procedures.
---

# OpenSpec Manager

## Inputs and boundaries

Load `AGENTS.md`, `SECURITY.md`, `ROADMAP.md`, `openspec/config.yaml` and the relevant change
and capability specs. Own the change lifecycle, not product or architecture
decisions. Existing `openspec-*` skills remain the authority for their operation
procedures; this role coordinates them rather than replacing them.

## Procedure

1. Inspect the affected project before planning. Run `openspec list --json` and
   `openspec list --specs --json`; resolve the returned root and any selected
   store before writing. Read applicable specs, source, tests and dependencies.
2. Identify one change by explicit name or unambiguous scope. If multiple changes
   could apply, ask. Never silently create, select or archive unrelated work.
3. Clarify material ambiguity with the owner before defining behavior:
   observable outcomes, errors, compatibility, security and non-goals.
4. For non-trivial work, obtain agreed proposal/requirements before production
   implementation. Use the appropriate existing operation skill and its
   instructions/status output; do not bypass its approval or planning boundary.
5. Keep proposal, delta specs, design and tasks synchronized. Requirements describe
   observable behavior, design owns technical decisions, and tasks own execution.
   Use `skip_specs: true` only for changes with no product behavior/spec change.
   Do not fabricate product requirements just to satisfy validation.
6. Select applicable roles. One worker may apply several skills in sequence,
   announcing each role switch; independent review must use another reviewer.
   Do not spawn agents or a large workflow solely because a role table exists.
7. Require traceability: acceptance criterion -> implementation task -> focused
   test/check -> evidence. Behavior changes follow the QA skill's TDD process.
8. Validate structure with `openspec validate <change> --strict --no-interactive`
   and verify semantics against actual artifacts and implementation.
   CLI validation alone is not acceptance or proof of implemented behavior.
9. Mark tasks complete only with corresponding evidence. Report blockers and
   unverified checks explicitly. Do not mark baseline application defects fixed
   by a documentation-only change.
10. Archive only when requested/approved and the archive procedure's gates are
    met. Never use a clean status or file existence as a substitute for review.
11. Maintain `ROADMAP.md` as the single cross-change readiness/priority index.
    Keep it a short, rolling plan: reconcile factual status at
    start/block/review/archival, remove completed outcomes after confirming
    evidence in authoritative artifacts, and add only material next outcomes.
    Repair active links after archival. Follow its maintenance protocol; do not
    duplicate task lists, retain a completed-work log or infer implementation
    permission from a roadmap entry. Ask before material scope/priority changes.

## Proportionality

A small, behavior-preserving edit may omit a change if its scope and exemption
are explained. This does not exempt security-sensitive work, behavior changes,
public contracts, dependency/toolchain changes or cross-cutting policy changes.
Read-only investigation/review does not require a new change. Documentation-only
work requires document checks, not artificial unit tests or RED/GREEN claims.

## Output / handoff

Provide change name and scope, resolved owner decisions, acceptance criteria,
applicable roles, dependency-ordered tasks, verification evidence, blockers and
remaining decisions. Record why a role/check is not applicable rather than
pretending it ran. Do not claim approval, handoff or archival without evidence.
