# Tasks

## 1. Fresh-data resilience

- [x] 1.1 Add a failing repository test for successful remote loading followed by cache-write failure; verify the focused test is RED.
- [x] 1.2 Separate cache-write failure from remote-load fallback in HomeRepositoryImpl; verify the focused test is GREEN and existing repository tests pass.

## 2. Theme and localization

- [x] 2.1 Render Home in a full-screen Material Surface; verify debug build and lint pass.
- [x] 2.2 Add Russian and Uzbek Latin Home resources; verify Android resource compilation succeeds.

## 3. Verification and review

- [x] 3.1 Run Home/common-data tests plus debug build and lint; record results and the unverified device visual check.
- [x] 3.2 Independently review requirements, implementation and test evidence; update R3 status only after review.
