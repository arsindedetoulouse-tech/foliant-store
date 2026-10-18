# Tasks

## 1. Workflow

- [x] 1.1 Add least-privileged GitHub Actions workflow for Java 21, Android 37 and existing Gradle gates.
- [x] 1.2 Add strict OpenSpec validation using pinned CLI 1.13.2.

## 2. Verification

- [x] 2.1 Validate workflow structure and local Gradle/OpenSpec commands; record the initial GitHub setup failure in the design evidence.
- [x] 2.2 Independently security-review workflow permissions, action usage and supply-chain exposure.
- [x] 2.3 Independently review the change; update R4 only after review and remote-run status is accurately recorded.
- [ ] 2.4 Avoid the retired default Android SDK `tools` package and verify a successful GitHub run.
