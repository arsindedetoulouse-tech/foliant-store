---
name: android-architect
description: Assess Foliant Android module boundaries, layer ownership, public contracts and technical design. Use before structural changes, new infrastructure or cross-feature integration.
---

# Android Architect

## Inputs and boundaries

Load `AGENTS.md`, `SECURITY.md`, the active change, agreed requirements and affected
module build files, sources and tests. Own technical design, not silent changes to
product behavior. Prefer the minimum change consistent with existing architecture.

## Layer ownership

- `presentation`: screens/routes, ViewModels, UI state/actions/side effects,
  presentation models and formatting.
- `domain`: business models, repository contracts, reusable validation/invariants
  and domain services only when complexity or reuse justifies them.
- `data`: repository implementations, sources, DTOs/entities, persistence and
  infrastructure mappings.

Dependency direction is presentation -> domain and data -> domain. Domain must
not depend on Android UI, Compose, navigation, networking, persistence or concrete
infrastructure. Data must not depend on presentation. Presentation must not use
DTOs/entities, Ktor types or concrete data implementations. Composition-root DI
wiring may reference implementations; that is not permission to use them in UI.

Use explicit DTO/entity -> domain mappings and appropriate outbound mappings.
Do not add UI selection, expansion, formatting, loading or transient flags to
domain models; use presentation models. Keep persistence-only state in data.

## Procedure

1. Map the affected module graph, public contracts and actual call paths.
   Preserve package names and module boundaries; avoid circular dependencies.
   Distinguish source conventions from boundaries enforced by Gradle visibility.
2. Search for prior art before adding a base class, provider, use case, handler
   or wrapper. Do not add a use case solely to delegate one repository call.
   Existing thin wrappers do not justify unrelated cleanup.
3. Keep public cross-feature contracts in `feature/*-api`; navigation arguments
   carry stable IDs/small serializable values, not callbacks, ViewModels,
   repositories, mutable state or large models.
4. Keep navigation/UI runtime dependencies at Route/navigation boundaries.
   Do not inject navigation lambdas, Compose objects, Activity or Fragment into
   long-lived services. Prefer explicit short-lived object construction there.
5. Define DI ownership and lifetimes. Keep module definitions near their owners
   and register modules in `app/src/main/kotlin/com/arsinde/foliantstore/App.kt`.
   Runtime Koin parameters are for genuine IDs/configuration, not UI callbacks;
   every parameterized resolution requires a focused test.
6. Use application Context only for necessary platform APIs, behind a focused
   abstraction when appropriate. Do not propagate Context through unrelated
   ViewModels, repositories or domain services.
7. Define coroutine ownership, cancellation and blocking-I/O dispatching.
   Do not introduce unmanaged scopes or duplicate dispatching around already
   asynchronous APIs.
8. Keep networking centralized in `core:network`; transport/serialization types
   stay at data/network boundaries. Reuse error/auth/storage contracts only if
   they actually exist. The skeleton has no approved auth/error/secret-storage
   architecture; introducing one requires explicit design and security review.
9. Use convention plugins and the version catalog; assess compatibility and
   public API impact before changing dependencies, SDKs or compiler targets.
10. For security-sensitive design, load the security role and obtain required
    review. Document alternatives, consequences and testability in the active
    change's design; create an ADR only if the task calls for a lasting decision.

## Output / handoff

Provide affected modules/APIs, responsibility and lifetime decisions, minimal
integration plan, data/error mappings, migration/compatibility impact and
verification requirements. Escalate product/security decisions instead of
inventing requirements. Mark existing violations as findings, not permission
to expand the implementation scope.
