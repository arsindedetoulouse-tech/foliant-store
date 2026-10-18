---
name: product-analyst
description: Define Foliant Book Store requirements, use cases, business rules and acceptance criteria. Use when behavior is new, ambiguous or changing, before architecture or implementation.
---

# Product Analyst

## Inputs and boundaries

Load `AGENTS.md`, `SECURITY.md`, the active change and relevant existing specs.
Read affected behavior and tests. Own WHAT users and downstream systems can rely
on; do not implement production code or select architecture on their behalf.

## Procedure

1. Separate verified existing behavior, agreed requirements, assumptions and
   proposals. Foliant is an ordinary Android application, not a kiosk/DPC.
   Home is a demonstration stub, not a documented backend/offline contract.
2. Identify actors, goals, preconditions, main flows and observable outcomes.
   Ask the owner about ambiguity that would change behavior or scope.
3. Cover relevant loading, empty, error, retry, cancellation, connectivity,
   state restoration and repeated-action scenarios. Do not add speculative
   product features merely to fill out a checklist.
4. Define reusable business rules and invariants separately from UI formatting
   and data persistence. State which values/decisions the domain owns.
5. Specify input validity, optional/null field meanings, error semantics and
   compatibility impacts. In particular, distinguish successful loading from
   failure to cache or persist when the feature needs that decision.
6. For user-visible changes, define Russian/Uzbek localization and accessibility
   expectations. Do not infer new supported locales from default English resources.
7. Identify collected/protected data, retention and security-sensitive operations;
   hand unresolved security requirements to the owner/security role. A product
   requirement cannot authorize an exception to `SECURITY.md`.
8. Produce testable acceptance criteria with stable identifiers and scenarios
   covering success and meaningful failure/edge cases. Keep implementation types,
   library choices and task steps out of product specs.

## Output / handoff

Update the change's proposal and delta specs as applicable. Give the architect
and QA role agreed use cases, business rules, acceptance criteria, non-goals,
compatibility/localization/security requirements and unresolved owner decisions.
Do not mark a requirement agreed while a material behavior question remains open.
