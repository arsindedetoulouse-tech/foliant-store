# Tasks

## 1. Inventory and hygiene

- [x] 1.1 Classify the untracked baseline by file names and paths without reading local secret content; record included/excluded categories in design.md.
- [x] 1.2 Add narrow ignore rules for local signing and private-key material; verify expected source, Android assets and OpenSpec artifacts are not ignored.
- [x] 1.3 Independently security-review the inventory and ignore rules; resolve Critical/High findings before requesting commit approval.

## 2. Owner-approved baseline

- [x] 2.1 Update ROADMAP.md to mark R2 in progress and link this change; verify R2 remains the sole active roadmap outcome.
- [x] 2.2 Present the proposed staging categories and exact approval boundary to the owner; do not stage or commit before approval.
- [ ] 2.3 After explicit approval, stage only the reviewed baseline, inspect staged names/diff for secrets or outputs, and create the initial commit with the required trailer.

## 3. Verification

- [ ] 3.1 Validate the OpenSpec change and independently review the final staged scope and commit evidence before marking R2 complete.
