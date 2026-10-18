# SECURITY.md

## Purpose

This document defines the mandatory security policy for Foliant Book Store, an
ordinary Android application currently at the skeleton stage. It does not assume
kiosk privileges, device-owner provisioning or a Device Policy Controller.

It is part of the project engineering contract and applies to developers, reviewers, automation, and AI agents.

`AGENTS.md` requires compliance with this document.

The current skeleton has no production backend, authentication or approved
secret-storage design. Requirements below constrain future features; they do not
claim those controls are already implemented. The security review procedure is
defined in `.agents/skills/security-reviewer/SKILL.md`.

## Security principles

- Never weaken a security control merely to make a feature, test, build, or debugging session succeed.
- Prefer least privilege.
- Minimize collection, storage, exposure, and logging of sensitive data.
- Keep security-sensitive behavior inside the appropriate architectural boundary.
- Treat credentials and authentication material as secrets.
- Security requirements take precedence over implementation convenience.

## Secrets and credentials

Never commit, print, copy into source code, or expose:

- passwords;
- access tokens;
- refresh tokens;
- API credentials;
- private keys;
- signing credentials;
- Firebase credentials;
- service-account credentials;
- repository credentials;
- production authentication material.

Sensitive or local-only project files include, where applicable:

- `keystore.jks`;
- `playstore_account.json`;
- `app/google-services.json`;
- `local.properties`;
- Maven repository credentials;
- signing credentials;
- Firebase credentials;
- Google Maps credentials;
- Facebook credentials;
- API credentials;
- environment-specific configuration containing protected values.

Do not remove sensitive/local-only files from `.gitignore`.

Before introducing signing or service-account files, add appropriate ignore
patterns and verify that no protected material is already tracked. Current ignore
rules are not a complete secret-prevention control; an ignored file is not
automatically safe, and client configuration files are not all secrets by nature.
Review the actual data rather than treating a filename as proof of exposure.

Do not introduce real credentials into examples, tests, fixtures, documentation, or generated files.

Use approved environment/configuration mechanisms. If the required mechanism
does not exist yet, define it in the change rather than placing credentials in
the client or claiming the skeleton already provides secure configuration.

## Logging

Never log:

- access tokens;
- refresh tokens;
- `Authorization` headers;
- passwords;
- OTP values;
- private keys;
- signing material;
- full user credentials;
- sensitive request or response bodies.

When diagnostic logging is necessary, redact sensitive values.

Do not enable broad request/response logging that can expose production user data or authentication material.

## Authentication and authorization

Authentication material MUST be handled only at the required architectural boundary.

Do not expose authentication tokens or credentials to presentation code unless explicitly required.

Authorization checks MUST NOT be bypassed to make a feature work.

Do not treat client-side UI visibility as a substitute for authorization.

When changing authentication or authorization behavior, perform a security review.

## Sensitive data

Collect and retain only data required by the feature.

Do not unnecessarily:

- persist sensitive data;
- put sensitive values into logs;
- expose sensitive values through UI state;
- place sensitive values in navigation arguments;
- copy sensitive values into analytics events;
- include sensitive values in crash/debug reports.

Use the narrowest data representation required by the operation.

## Storage and backup

Sensitive data MUST use an explicitly approved storage mechanism and protection
model. If none exists, define and review it before persisting sensitive data.
Preferences DataStore in the current skeleton is not an approved secret store.

Do not store secrets in:

- plain-text resources;
- source files;
- unprotected local databases;
- preferences intended for non-sensitive configuration;
- test fixtures containing production data.

Do not broaden Android backup exposure for sensitive data.

Before storing protected data, explicitly decide backup and device-transfer
eligibility, retention/deletion and any necessary exclusion rules for supported
Android versions. Do not rely on platform defaults to protect such data.

When storage or backup behavior changes, evaluate the security impact.

## Network security

Use the existing network configuration in `core:network`.

Do not:

- disable TLS certificate validation;
- accept invalid certificates as a workaround;
- bypass hostname verification;
- introduce insecure HTTP for protected production traffic;
- log authentication headers or sensitive response bodies.

Certificate pinning or other network security controls MUST NOT be removed or weakened without an explicit security decision and review.

This does not require inventing certificate pinning for the skeleton. New
endpoints, authentication and response/error handling require an agreed contract;
centralized Ktor setup alone is not proof of those controls.

## Android permissions

Use the minimum permission scope required by the feature.

Do not add a permission without a concrete feature requirement.

When adding or broadening a permission:

- document why it is required;
- use the narrowest available permission;
- review the resulting data/access exposure.

## Exported components

Components exposed outside the application MUST have intentional exposure.

Review:

- exported activities;
- services;
- receivers;
- providers;
- intent filters;
- deep links.

Do not export components merely to make integration or testing easier.

## Intents and deep links

Treat external intent and deep-link input as untrusted.

Validate:

- identifiers;
- parameters;
- URLs;
- actions;
- destinations;
- authorization context.

Do not allow external input to bypass authentication, authorization, validation, or navigation constraints.

## WebView

WebView content and bridge interfaces require explicit security review.

Do not:

- enable unnecessary JavaScript;
- expose sensitive native interfaces unnecessarily;
- load untrusted content into privileged contexts;
- bypass URL/content validation.

## Firebase and external services

Do not commit service-account credentials or other protected credentials.

Do not expose administrative credentials to the client application.

When changing Firebase configuration, authentication, analytics, crash reporting, or remote configuration, evaluate:

- data collection;
- sensitive data exposure;
- access control;
- configuration integrity.

## R8 / ProGuard

Do not disable shrinking, optimization, or obfuscation merely to work around a release-build problem.

Do not use broad rules such as:

```proguard
-keep class ** { *; }
```

Prefer the narrowest rule required by:

- serialization;
- reflection;
- JNI;
- framework requirements;
- affected libraries.

## Dependencies

Do not introduce dependencies with unknown or unacceptable security implications.

For security-sensitive or infrastructure dependencies, consider:

- source/repository trust;
- maintenance status;
- required permissions;
- transitive dependencies;
- known vulnerabilities;
- data handling.

Do not silently upgrade unrelated dependencies as part of a focused security change.

When introducing or changing Gradle wrapper distributions, verify the official
distribution checksum and configure `distributionSha256Sum`. Treat wrapper-binary
provenance and dependency verification as separate controls; HTTPS alone does
not establish artifact integrity. Report unverified provenance rather than
claiming a dependency or wrapper security audit has passed.

## AI-agent security rules

The AI agent MUST:

- treat repository secrets as inaccessible implementation inputs;
- never reproduce secret values in output;
- never create fake-looking production credentials and imply that they are valid;
- avoid reading sensitive files unless the task explicitly requires security analysis and access is permitted;
- redact secrets from diagnostics;
- stop when a requested implementation conflicts with this policy;
- request or record a security decision rather than inventing an exception.

The agent MUST NOT use a secret found in the repository as a convenient configuration value.

## Security-sensitive change workflow

Security-sensitive changes follow:

```text
OpenSpec
    ↓
Security impact identification
    ↓
Implementation
    ↓
Automated tests
    ↓
Security review
    ↓
Code review
    ↓
OpenSpec verification
```

`security-reviewer` MUST be involved before final approval.

Load `.agents/skills/security-reviewer/SKILL.md` before security-sensitive work.
The implementation author cannot provide their own independent final security
approval. Review actual new/untracked files as well as tracked diffs.

## Severity

Use:

```text
CRITICAL — severe compromise with demonstrated or credible exploit conditions
HIGH     — serious exploitable weakness or credential exposure requiring remediation
MEDIUM   — meaningful weakness with limited impact or significant prerequisites
LOW      — limited-impact weakness or defense-in-depth improvement
```

Assess severity using actual impact, exposure and exploit prerequisites, not
merely the presence of a security-related setting. Distinguish confirmed
vulnerabilities from hardening opportunities and unanswered product questions.

Unresolved CRITICAL and HIGH findings block approval. An agent or role cannot
accept them on the owner's behalf. Any proposed exception requires an explicit
owner security decision and policy review; ordinary task approval is not an
exception and cannot silently weaken this policy.

## Vulnerability handling

Do not publish exploit details, credentials, private keys, or sensitive reproduction data in ordinary project documentation or issue trackers.

Security vulnerabilities should be reported through the project's designated private security-reporting process when one exists.

If no project-specific process is defined, record the need for one rather than inventing a public disclosure workflow.

No private reporting contact or supported-release policy has been established
for this skeleton. Before external distribution, the owner must define them.
Do not invent an address, publish sensitive findings or claim a public issue
tracker is an approved private channel.

## Security review evidence

A security review should record:

- affected components;
- attack surface;
- data involved;
- security controls evaluated;
- findings and severity;
- mitigations;
- residual risks;
- approval/block status.

## Policy conflicts

If OpenSpec, a task request, debugging instruction, or implementation convenience conflicts with this document:

1. Do not weaken the security control.
2. Identify the conflict.
3. Explain the affected requirement.
4. Request or record the necessary security decision.
5. Continue only within the approved security boundary.
