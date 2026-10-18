# Development Readiness Roadmap

Last reviewed: 2026-10-18.

## Purpose and authority

This is a short, rolling, shared plan for the next highest-level outcomes in
Foliant Book Store. Read it before planning or implementing work. It records
priorities, dependencies and readiness, not product requirements or permission
to execute the next item.

`AGENTS.md` and `SECURITY.md` remain mandatory. Agreed product behavior belongs in
OpenSpec specs; each non-trivial implementation needs its own agreed change.
Detailed tasks and verification evidence belong in that change, not duplicated
here. A direct owner request takes precedence over roadmap ordering, but does not
override policies or approval gates. Ask if it conflicts with agreed scope.

Keep this file deliberately small: it contains only current, future or blocked
top-level outcomes. When an outcome is completed, remove its row and preserve its
evidence in the relevant OpenSpec change, archive and Git history. Add a new row
only when it is a material next outcome, not a subtask, defect list or a duplicate
of `tasks.md`. Replace obsolete rows rather than accumulating a completed-work log.

## Current position

- The next outcome is **R4**. It can be planned independently; no CI baseline
  change is active yet.

## Plan

Suggested order is R4 -> first product implementation.
Dependencies below are explicit; the row order does not introduce additional
dependencies. Product discovery in R5 may run in parallel with engineering work.

| ID | Outcome | Status | Prerequisites | Completion evidence |
|---|---|---|---|---|
| R4 | Minimal automated gates | planned | Safe versioned baseline | Successful CI run verifies its agreed gate set. |
| R5 | Agreed first product slice | planned | Product discovery may start now; implementation waits for R4 | Owner-approved product change with testable acceptance criteria. |

## Status and maintenance protocol

Statuses: `planned` (not started), `in_progress` (explicitly selected and active),
`blocked` (cannot progress without a stated prerequisite/decision) and `deferred`
(owner postponed it). Completed outcomes are removed rather than marked `done`.

1. At task start, read this file and check actual source, active changes and
   relevant evidence. Do not assume the snapshot above is still true.
2. Map the requested work to an existing ID or ask about a genuine scope conflict.
   Before non-trivial implementation, agree its OpenSpec change. Link it from the
   concise Current position only when the link is needed to explain active work.
3. Update the affected row and Current position in the same delivery as a
   start, blocker, completed change or changed verification outcome. Describe the
   blocker/next action and link to evidence; do not copy full logs or sensitive data.
4. When an outcome is complete, confirm its evidence in the change/review/commit,
   remove its row and revise Current position. Do not retain it as `done`, copy
   its history here or add a transition log. A completed `tasks.md` or CLI
   validation alone is insufficient.
5. Add a replacement item only when it is a top-level upcoming outcome. Keep the
   table small by consolidating related work and moving defect inventories,
   decisions, detailed tasks and historical evidence to their authoritative
   OpenSpec artifacts. Do not create a second competing backlog in AGENTS or skills.
6. If new evidence invalidates a previously completed outcome, add a concise new
   row for the renewed outcome with a link to its historical evidence. Material
   scope, ordering or acceptance changes need owner agreement; agents may update
   factual status without inventing product decisions.
7. After review or archival, repair active links to relocated artifacts. Update
   Last reviewed only when status, evidence, links or agreed priorities are
   actually reconciled; do not bump it merely because an agent opened this file.
