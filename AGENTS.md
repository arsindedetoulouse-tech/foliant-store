# Engineering Contract

## Project and verified baseline

Foliant Book Store is a multi-module Kotlin Android application skeleton. Product features are defined through SDD;
the current Home flow is a JSON-asset demonstration with a cached ID.

Stack: Jetpack Compose, Orbit MVI, Koin, Ktor, Kotlin Serialization, Navigation 3,
Gradle Kotlin DSL and convention plugins in `build-logic`.
Compiler target is JVM 21; compile/min/target Android SDKs and the Gradle daemon
JVM are defined by convention plugins and `gradle/gradle-daemon-jvm.properties`.
Do not assume the daemon JVM and compiler target must be identical.

Supported app locales are Russian (`ru-RU`) and Uzbek Latin (`uz-Latn-UZ`).
Default resources must remain valid. Existing Home translations are incomplete.

| Area | Responsibility |
|---|---|
| `app/` | Application/Activity and application DI composition |
| `app/navigation/` | Start route, top-level routes and entry-provider composition |
| `feature/`, `feature/*-api/` | Feature implementations and public cross-feature contracts |
| `common/{domain,data,presentation,testing}/` | Shared layers and test dependencies |
| `core/{network,datastore,navigation,designsystem}/` | Reusable technical infrastructure |
| `build-logic/`, `gradle/libs.versions.toml` | Build conventions and dependency catalog |
| `openspec/` | Project context, capability specs and scoped changes |
| `ROADMAP.md` | Shared priorities, readiness, dependencies and links to changes |
| `.agents/skills/` | Role procedures and existing OpenSpec operation skills |
| `SECURITY.md`, `CHANGELOG.md` | Security policy and change history |

There is no implemented production backend, authentication, `ApiErrorResponse`
contract, approved secret-storage mechanism or custom testing helper library.
Do not describe these as existing or add them without requirements.
`docs/` is not an established documentation location; put SDD artifacts in
OpenSpec rather than introducing scratch documentation into the source tree.

## Mandatory rules

- Read this contract, `SECURITY.md` and [ROADMAP.md](ROADMAP.md) for every task.
  The roadmap guides scope/priority, not automatic execution or policy exceptions.
  Update affected roadmap status/evidence with the work; follow its maintenance
  protocol and keep detailed tasks in the relevant OpenSpec change.
  Before role-specific work,
  load the corresponding `.agents/skills/<role>/SKILL.md`. Before non-trivial
  modifications, identify and read the agreed OpenSpec change.
- Security constraints are mandatory and cannot be overridden by a spec, skill,
  debugging request or convenience. Stop on conflict; use `SECURITY.md` for
  security decisions and findings. Role procedures cannot relax this contract.
- Inspect relevant source, dependencies and tests before editing. Preserve
  package/module boundaries and owner changes; keep edits focused.
- Do not modify generated files, build outputs, IDE files or local configuration.
  Do not delete or overwrite unrelated tracked/untracked work. A clean/empty
  diff is not evidence about untracked files.
- Preserve presentation -> domain and data -> domain dependency direction.
  Domain must remain independent of Android UI, Compose, navigation, network,
  persistence and concrete infrastructure. Data must not depend on presentation.
- Presentation must not use DTOs, entities, Ktor types or concrete data
  implementations. Composition-root DI wiring may reference implementations,
  but UI/ViewModels must depend on domain contracts. Use explicit model mappings.
- Keep UI formatting/selection/transient state in presentation and persistence
  details in data. Reusable business invariants belong in domain. Do not add
  abstractions or one-call use cases without architectural/business value.
- Keep navigation, UI callbacks and UI-scoped objects at UI boundaries, not in
  long-lived services or Koin runtime function parameters. Genuine parameterized
  Koin construction uses explicit `parametersOf(...)` and resolution tests.
- Use structured concurrency; preserve cancellation. Do not perform blocking
  work on the main thread, create unmanaged scopes or hide failures in silent
  defaults. Surface failures through established, appropriately redacted mechanisms.
- Keep Route collection/navigation separate from stateless/hoisted Screen UI.
  Use lifecycle-aware collection and one-time effects; do not execute external
  side effects during ordinary Compose rendering. Reuse the design system.
- Put user-facing text in resources and maintain supported locales. Use immutable
  UI state; do not expose writable flows or mutable implementation state.
- Use convention plugins, version catalog and existing helpers. Do not duplicate
  convention-provided dependencies or introduce circular module dependencies.
  Keep public feature contracts in the appropriate `*-api` module.
- New features and non-trivial behavior fixes require TDD before production
  changes. Run the narrowest relevant checks, update directly related docs/tests,
  and run a debug build for Android configuration/resource changes.
- Approval requires independent review, plus independent security review for
  security-sensitive changes. Never self-declare implementation independently
  reviewed or report a blocked/unexecuted check as passing.

## SDD lifecycle and approvals

For non-trivial work:

```text
Investigate -> clarify with owner -> agreed OpenSpec scope/requirements
-> applicable design/security assessment -> TDD implementation
-> focused verification -> independent review(s) -> OpenSpec verification
-> archive only when requested/approved
```

Use the existing OpenSpec operation skill for the requested operation. Respect
its approval/planning boundaries; planning is not implicit authorization to apply.
Ask before deciding material behavior, compatibility, security or scope ambiguity.
Do not silently select another change or invent missing requirements.

A genuinely small behavior-preserving edit may omit a change with an explicit
scope/exemption explanation. Behavior changes, security-sensitive work,
public-contract changes, dependencies/toolchains and cross-cutting policies are
not exempt. Read-only review needs no new change. Documentation-only changes
need document checks, not invented product specs or runtime TDD evidence.

Each applicable handoff needs evidence: change/acceptance criteria, decisions,
changed artifacts, tests written before implementation, commands/results,
review findings and unresolved risks. Record checks/roles that are not applicable
or blocked with reasons. Structural validation is not semantic approval.

## Role selection

Load only applicable roles and announce role switches. Roles are procedures, not
a requirement to spawn seven agents or a large workflow. One worker may carry
several roles sequentially; independent review must use a different reviewer.

| Role / skill | When to load |
|---|---|
| [openspec-manager](.agents/skills/openspec-manager/SKILL.md) | Change scope, lifecycle, approvals, verification and archival |
| [product-analyst](.agents/skills/product-analyst/SKILL.md) | New/ambiguous behavior, business rules and acceptance criteria |
| [android-architect](.agents/skills/android-architect/SKILL.md) | Module/API boundaries, infrastructure, cross-feature or structural design |
| [android-developer](.agents/skills/android-developer/SKILL.md) | Kotlin/Android implementation and integration |
| [qa-engineer](.agents/skills/qa-engineer/SKILL.md) | TDD, deterministic tests and verification strategy/evidence |
| [code-reviewer](.agents/skills/code-reviewer/SKILL.md) | Independent code or engineering-documentation review |
| [security-reviewer](.agents/skills/security-reviewer/SKILL.md) | Security-sensitive assessment, implementation and independent review |

Security-sensitive work includes auth/authorization, credentials/protected data,
storage/backup, network/TLS, permissions, exported components, intents/deep links,
WebView, Firebase security, sensitive logging and R8/ProGuard security settings.
The full controls and approval rules are in `SECURITY.md`.
