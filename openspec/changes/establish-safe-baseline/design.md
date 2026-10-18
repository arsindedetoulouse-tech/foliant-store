# Design

## Context

The working tree began entirely untracked. Existing `.gitignore` excludes Gradle,
IDE/build outputs, `local.properties` and IntelliJ module files, but lacks
defensive patterns for common signing credentials. `local.properties` exists and
must not be read for this task. No signing/service-account file is known from
the untracked inventory; filenames and ignore behavior will be assessed without
opening protected contents.

## Goals / Non-Goals

**Goals:**

- Establish an auditable, minimal initial source baseline.
- Protect common local/signing artifacts from accidental staging.
- Make commit scope explicit and owner-approved.

**Non-Goals:**

- Scan, print or rotate credentials.
- Add a secret scanner, CI, release signing, Firebase or a public disclosure
  policy.
- Rewrite history, commit generated outputs or add product behavior.

## Decisions

1. **Use filename/ignore metadata, not secret content.** This fulfills baseline
   hygiene without turning local credentials into implementation inputs.
2. **Ignore known local signing patterns narrowly.** Add conventional keystore
   and signing-property patterns, plus named local service-account/private-key
   patterns where appropriate. Do not blanket-ignore public Android configuration
   or all JSON files because some are required non-secret project assets.
3. **Separate staging approval from inspection.** Inspection and changes can
   prepare a proposed baseline. `git add`/`git commit` happen only after the
   owner explicitly approves the exact reviewed scope.
4. **Do not modify `local.properties`.** Its existing ignore rule is preserved;
   any machine-specific SDK state remains local.

## Risks / Trade-offs

- [A secret uses an unanticipated filename] -> Ignore patterns reduce accidental
  staging but do not replace review or secret scanning; inspect the proposed
  staged-name list before commit.
- [A broad pattern hides required configuration] -> Limit patterns to private
  key/keystore/signing-file names and test that tracked source/assets remain visible.
- [All files are initially untracked] -> Classify and approve the initial scope
  before the first staging action; do not rely on an empty diff.

## Migration Plan

1. Classify the untracked inventory and evaluate ignore behavior.
2. Apply narrow hygiene rules and independently security-review the result.
3. Present the exact proposed staging scope for owner approval.
4. Stage and commit only after approval; recheck staged names and verify no
   ignored/local output is included.

## Inventory and Security Evidence

The untracked baseline contains Kotlin/Gradle source and configuration, Android
resources/assets, Gradle wrapper, project policies, role skills and OpenSpec
artifacts. Ignored local/generated categories are `.gradle/`, `.idea/`, build
directories and `local.properties`; the latter was not read. No untracked file
name matched the signing/service-account/private-key patterns.

`.gitignore` now narrowly excludes local signing (`*.jks`, `*.keystore`,
`*.p12`, `*.pfx`, `key.properties`, `signing.properties`) and protected
credential names (`playstore_account.json`, `service-account*.json`,
`private-key*.pem`). Checks confirm these candidate names are ignored while
`app/google-services.json`, the Home JSON asset, wrapper JAR and skeleton metadata
remain visible. No blanket JSON rule was added.

Initial independent security review found a High supply-chain blocker: no Gradle
distribution checksum and unverified wrapper JAR provenance. Remediation:

1. Retrieved the official checksum from
   `https://services.gradle.org/distributions/gradle-9.7.0-bin.zip.sha256`:
   `84fbba45c7f4c64abc77460e1c00f541e9f960e3c7ed2538f1ede19eacd873ae`.
2. Downloaded the matching official ZIP and verified its SHA-256 locally against
   that value.
3. Generated a wrapper JAR using the verified extracted Gradle 9.7.0 distribution
   in a temporary minimal build. Replaced the project JAR with that artifact:
   `7a9ce74cff467ca1bf60a4fcd9f05185acceda4d0f382434d393e17864262c5d`.
4. Added the verified distribution checksum to
   `gradle/wrapper/gradle-wrapper.properties`.

The temporary verified distribution is not part of the repository baseline.
The old wrapper JAR hash was
`76805e32c009c0cf0dd5d206bddc9fb22ea42e84db904b764f3047de095493f3`;
it is retained here as remediation evidence, not as a claim of compromise.

Residual decisions: `app/google-services.json` is not present. Before introducing
it, decide whether it is versioned public client configuration or local protected
configuration. Ignore rules remain defense in depth; final staged-name and diff
review remains mandatory.

Follow-up independent security review confirmed the configured distribution
checksum and wrapper JAR hash, the narrow ignore behavior and absence of a
confirmed finding in scope. It did not repeat the external download/generation,
read local properties or inspect a staged commit; those limits remain explicit.

The proposed initial baseline is 111 non-ignored files:

- project sources, Gradle/build logic and wrapper;
- Android modules, resources and Home asset;
- policies, roadmap and role/OpenSpec operation skills;
- OpenSpec configuration, archive placeholders and active changes;
- repository metadata such as `.gitignore` and `.android-skeleton.json`.

It excludes local/generated categories matched by `.gitignore`, including
`local.properties`, IDE/Gradle/build outputs and the newly covered signing/private
credential names. The owner approved this exact baseline scope; staging is
complete.

## Completion Evidence

After explicit owner approval, 111 reviewed non-ignored files were staged.
The staged path review found no local configuration, generated output, signing or
protected credential path; `git diff --cached --check` passed. Independent staged
review found no baseline-scope blocker after its approval-status documentation
finding was corrected.

Initial baseline commit:

```text
f1c19ca110c868794b817919e5bc2ce171c860c5
chore: establish safe baseline
```

The commit has the required Copilot co-author trailer. `git status --short` was
empty immediately after it. R2 has been removed from the rolling roadmap; R3
and R4 are the remaining next outcomes. This documentation update records the
post-commit state and is separate from the initial baseline commit.
