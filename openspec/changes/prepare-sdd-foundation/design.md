# Design

## Context

See `proposal.md` for motivation and agreed scope. All initial project files were
untracked, so baseline review examines the current snapshot rather than a Git diff.
The seven role skills required by the original contract were missing. Creating
their initial definitions is the bootstrap exception for this documentation change;
no earlier role-skill compliance is claimed.

## Goals / Non-Goals

**Goals:** Make policy loading practical, preserve architectural and security
invariants, and make requirements-to-verification handoffs auditable.

**Non-Goals:** Implement product features, fix application defects, select a backend,
introduce auth/error abstractions, add CI, change toolchains or establish a release baseline.

## Decisions

1. **Global contract plus role procedures.** Keep enforceable invariants in `AGENTS.md`;
   put detailed checklists and examples in role skills. Moving every constraint
   into optional skills would let agents bypass important rules by skipping a role.
2. **Seven roles, not seven mandatory agents.** Skills are procedures, not a mandate
   to spawn a multi-agent workflow. Use applicable roles; independent review must
   use a different reviewer. Explicitly record genuinely inapplicable work.
3. **Security has one authority.** `SECURITY.md` owns security requirements; the
   security skill owns the review method. No skill or OpenSpec artifact grants an
   exception. Unresolved critical/high findings block approval.
4. **Proportionate SDD.** Non-trivial work starts with agreed OpenSpec scope.
   Small behavior-preserving edits can explain an exemption; security-sensitive
   or behavior-changing work cannot use that shortcut. This cross-cutting
   documentation change has its own change with `skip_specs: true`, rather than
   artificial product requirements.
5. **Facts are not aspirations.** Current Home loads a JSON asset and caches an ID.
   There is no implemented authentication, `ApiErrorResponse`, DPC, approved secret
   storage or production backend. New infrastructure requires its own requirements.
6. **Do not archive automatically.** Keep this change available for owner review.
   Failed Android validation is a baseline limitation, not a documentation pass or
   evidence that runtime behavior is correct.

## Baseline Review

These findings are not application changes in this task.

| Priority | Evidence | Finding / next decision |
|---|---|---|
| Process blocker, addressed here | Original `AGENTS.md`, role skill inventory | Seven required role definitions were absent; product identity and app package path were wrong. |
| Medium | `common/data/src/main/kotlin/com/arsinde/foliantstore/data/home/HomeRepositoryImpl.kt:10-13` | Cache-write failure enters the same fallback as load failure, discarding a successful fresh load. Define cache failure semantics before a focused fix. |
| Medium | `feature/home/src/main/kotlin/com/arsinde/foliantstore/feature/home/presentation/HomeScreen.kt:37-43`, `core/designsystem/src/main/kotlin/com/arsinde/foliantstore/designsystem/AppTheme.kt:55-59`, `app/src/main/res/values/themes.xml:8` | Dark Compose palette has no root themed surface over the light platform window. Verify visually and fix in a separate UI change. |
| Medium | `feature/home/src/main/res/values/strings.xml:5-8`, `app/src/main/res/xml/locale_config.xml:3-4` | Home has no Russian/Uzbek translations despite advertised app locales. |
| Configuration drift | `gradle/libs.versions.toml:2`, `build-logic/build.gradle.kts:12` | AGP versions differ: 9.4.1 vs 9.4.0. Align in a separate build change after compatibility checks; no build failure attributed to this without evidence. |
| Architectural decision | `feature/home/build.gradle.kts:11` | Feature exports common data through `api` for DI; source layering exists but is not enforced by Gradle visibility. Decide composition-root boundaries before tightening dependencies. |
| Test gap | Existing repository, navigation and ViewModel tests | Sixteen tests cover useful state behavior, but not cache-write failure, actual asset/DataStore integration, DI startup, process restoration or rendered navigation. |

Missing CI/static-analysis configuration is a readiness decision, not a proven
runtime defect. A pass-through Home use case and an empty testing helper module
do not justify unrelated refactoring.

## Security Baseline

Read-only inspection found no confirmed exploitable vulnerability in the current
stub. Scope: manifest exposure/permissions, centralized Ktor and DataStore setup,
release shrinking, wrapper transport and ignore patterns.

- The launcher activity is intentionally exported; no DPC, WebView, auth flow or
  deep-link endpoint exists in the reviewed skeleton.
- The Ktor client does not bypass TLS or enable sensitive logging. It has no
  production endpoint/auth/error contract. Preferences DataStore is not secret storage.
- Backup/data-extraction policy is not explicitly configured. Before storing
  protected data, decide what can be backed up and test the resulting rules.
- `.gitignore` lacks defensive patterns for signing/service-account material;
  no such material was identified or read. Add appropriate exclusions in a separate
  repository-hygiene change; ignore rules do not replace secret detection.
- Wrapper transport is HTTPS but has no `distributionSha256Sum`; wrapper and
  dependency integrity controls are a separate supply-chain hardening task.
- Release shrinking and resource shrinking are enabled. No broad keep-all rule
  or intentional TLS weakening was observed.

This is not a dependency CVE audit, binary provenance verification, release APK
assessment or device test. A private vulnerability-reporting contact and product
data/auth/backup requirements remain owner decisions.

## Risks / Trade-offs

- [Rules drift from code] -> Reference authoritative Gradle/resource files and
  update context with relevant changes; do not copy version pins into every skill.
- [Skill loading hides requirements] -> Keep invariant summaries and routing global.
- [Review of untracked files appears empty] -> Require snapshot review and an explicit file inventory.
- [Application validation unavailable] -> Record failures below; do not claim tests passed.

## Validation Evidence

Baseline command:

```sh
./gradlew :common:data:testDebugUnitTest :feature:home:testDebugUnitTest :core:navigation:testDebugUnitTest :app:assembleDebug :app:lintDebug --console=plain --quiet
```

It failed before compilation: Gradle could not download its required JDK 25 via
the configured Foojay/Temurin URL. A retry with installed JDK discovery and
downloads disabled also failed to locate a matching installation:

```sh
./gradlew -Dorg.gradle.java.installations.paths=/usr/lib/jvm/java-25-openjdk-amd64 -Dorg.gradle.java.installations.auto-download=false --offline :common:data:testDebugUnitTest :feature:home:testDebugUnitTest :core:navigation:testDebugUnitTest :app:assembleDebug :app:lintDebug --console=plain --quiet
```

The local `java -version` reports 25.0.5-ea; compatibility with the daemon's
toolchain selection was not established. Java/Kotlin compile target 21 and
Gradle daemon JDK 25 are different settings. No local/generated configuration
was changed to bypass the problem.

## Migration Plan

Apply documentation and role definitions together. Validate references, skill
metadata, OpenSpec context and change structure; independently review the result.
No runtime migration applies. Reverting only this documentation change restores
the former contract without touching the application snapshot.

## Documentation Verification and Review

- `openspec validate prepare-sdd-foundation --strict --no-interactive` passed;
  product specs are intentionally not applicable (`skip_specs: true`).
- `openspec context --json` resolved this repository without context errors.
- A standard-library Python check validates all seven role names/descriptions,
  explicit handoff headings, role links, package references, ASCII skill content
  and trailing whitespace. Missing/inconsistent handoff headings were normalized
  during validation; no runtime behavior changed.
- Independent review applied the code-review and security-policy skills to the
  documentation set and found no blockers, major issues or weakened security
  controls. It separately confirmed OpenSpec validation and skill routing.
- Applied roles: OpenSpec manager (scope/lifecycle), Android architect (contract
  boundaries), security reviewer (policy), QA engineer (document checks) and
  independent code reviewer. Product ambiguity was resolved directly with the
  owner; a separate product-analysis phase and Android production implementation
  were not applicable.
- AC1 maps to the corrected product/baseline in AGENTS and context; AC2 to seven
  role definitions; AC3 to mandatory global invariants; AC4 to lifecycle and QA/
  review procedures; AC5 to SECURITY and independent policy review; AC6 to the
  document checks and restricted edit inventory.
- All intentional edits are to engineering documents, role skills and this
  OpenSpec change. Kotlin, Gradle, resources, manifests, generated operation skills,
  local configuration and CHANGELOG were not edited by this task.
- Because the initial tree is entirely untracked and no before-snapshot was
  available, Git cannot independently prove historical runtime-file equivalence.
  `git diff --check` has no coverage of untracked files; explicit document checks
  were used instead. Runtime checks remain blocked as described above.

Documentation is ready for owner review. This is not application release
approval, a passing Android baseline or automatic permission to archive.

## Roadmap Follow-up

At the owner's subsequent request, extend this still-unarchived documentation
change with a root `ROADMAP.md`. It records the proposed five-step readiness plan,
not product requirements or authorization to apply future changes.

Root placement makes the index discoverable without relying on session history,
agent memory or archived artifacts. AGENTS requires reading it; OpenSpec context
points to it; the manager skill coordinates updates. Store only cross-change
priority/status/dependencies and evidence links there. Specs, design and tasks
remain authoritative for their respective detailed content.

R1 is blocked on observed build-toolchain failure; other work is planned, not
implicitly started. Dependencies are explicit, allowing product discovery and
repository-hygiene inspection to proceed independently where appropriate.
Agents update factual status with their delivery; material scope/priority changes
need owner agreement. Completion requires evidence, links must survive archival,
and stale "current" facts must be reconciled rather than repeated indefinitely.

AC7 is checked through roadmap content, entry-point links, document consistency
and independent review. This follow-up does not modify runtime files or require
Android tests, production TDD or a new security assessment.

Follow-up verification: a standard-library Python check passed for the five
plan rows, three agent entry points, local links, AC7 and document whitespace.
`openspec validate prepare-sdd-foundation --strict --no-interactive` passed;
`openspec context --json` resolved the project without errors. Independent
documentation review found no blockers or major issues and confirmed preservation
of the five steps, approval boundaries, maintenance protocol and valid links.
The roadmap work itself remains unstarted; only its documentation is complete.

## Rolling Roadmap Refinement

The owner clarified that `ROADMAP.md` must be a compact rolling plan, not a
permanent programme ledger. Its Purpose and authority therefore define it as a
current/future/blocked outcome index. Completed rows are removed after their
evidence is retained in the relevant OpenSpec artifacts, archive and Git history;
new material outcomes replace them. Detailed tasks, findings and transition
history remain outside the roadmap.

The status model deliberately excludes `done`, and the roadmap has no transition
log or candidate-scope table. This prevents a growing backlog/history while
retaining concise, active links in Current position when necessary. The manager
skill and OpenSpec context enforce the same lifecycle. A later regression of a
completed outcome creates a concise new active row linked to prior evidence.

Independent review initially found leftover historical/detail content in Current
position and Completion evidence. It was removed: the current roadmap now gives
only an active R1 evidence link and concise outcome-level completion evidence.
The review finding was retained here rather than in the roadmap.
