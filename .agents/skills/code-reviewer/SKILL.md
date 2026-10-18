---
name: code-reviewer
description: Independently review Foliant requirements, architecture, changes and test evidence for correctness and scope. Use for code or engineering-documentation review before approval.
---

# Code Reviewer

## Inputs and independence

Load `AGENTS.md`, `SECURITY.md`, the active change and relevant specs/design/tests.
Inspect the actual affected files and evidence, not only the implementer's summary.
The reviewer must be independent of the implementation author. Do not silently
rewrite the implementation; return findings for the implementer.

For the initial untracked skeleton, review a bounded current-file inventory:
an empty `git diff` does not mean there is nothing to review. Exclude generated
outputs, IDE/local configuration and secret contents. For later changes, inspect
tracked diff plus relevant untracked/new files without disturbing owner changes.

## Review procedure

1. Check agreed requirements, acceptance criteria and scope. Distinguish existing
   defects, introduced regressions, missing requirements and optional improvements.
2. Follow relevant paths through UI, domain and data; check public contracts,
   module boundaries and concrete dependency lifetimes. Do not infer enforcement
   from an architecture diagram when Gradle visibility says otherwise.
3. Check error, fallback, cancellation, concurrency, restoration and repeated-event
   behavior where affected. Flag a bug only with a plausible failing scenario.
4. Check Route/Screen ownership, immutable state, Orbit effects, lifecycle-aware
   collection, navigation arguments, DI wiring and localization where applicable.
5. Check API/DTO/entity mappings and null/optional/error semantics end to end.
   Verify source facts before referring to an "existing" abstraction.
6. Assess tests against behavior and acceptance criteria. Confirm a regression
   test would fail without the fix. Distinguish real RED/GREEN output from a
   statement of intention and direct tests from integration/device evidence.
7. Check Gradle/convention/catalog consistency, compatibility and directly related
   docs. Do not turn a narrow review into an unrelated dependency/tooling overhaul.
8. For policy/skill changes, check metadata, routing, mandatory global invariants,
   absence of contradictory approvals/exemptions and fact-versus-plan wording.
   No security control may be weakened by a role procedure.
9. Identify security-sensitive impact and require the security role's independent
   evidence. A general review is not a substitute for a security assessment.

## Output / handoff

Report findings first, ordered by impact and confidence. Each substantive finding
needs file/line evidence, failure scenario/violated criterion and minimal corrective
direction. Separate blockers/major issues from optional suggestions and open
product decisions. Do not promote hypothetical future features to current bugs.

State reviewed scope, evidence used and limitations. If nothing substantive was
found, say so without implying all possible runtime/security behavior was verified.
Unresolved blockers/major issues prevent approval; accepted non-security residual
risks need an explicit owner decision. Security findings follow `SECURITY.md`,
not an invented exception in a general review.
