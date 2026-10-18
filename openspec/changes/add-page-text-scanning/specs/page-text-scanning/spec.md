# Spec Delta

## Purpose

Lets a user capture page text within a camera frame, combine multiple
on-device OCR results, and save the accumulated result as a local Markdown file.

## ADDED Requirements

### Requirement: User can start a framed page scan
The system SHALL expose a localized Scan action on Home. After the user grants
camera access, it SHALL show a camera preview with a visible recognition frame
and a manual capture action.

#### Scenario: Camera access is granted
- **WHEN** the user selects Scan on Home and grants camera access
- **THEN** the system displays the framed camera preview and manual capture action

#### Scenario: Camera access is denied
- **WHEN** the user denies camera access
- **THEN** the system displays a localized explanation and lets the user retry the request

### Requirement: Captured text is recognized on device
The system SHALL recognize Russian, Uzbek Latin, and English text from the area
inside the visible recognition frame only after the user manually captures it.
It SHALL not upload captured images or recognized text, and SHALL discard the
captured image after recognition.

#### Scenario: Text is recognized
- **WHEN** the user captures a frame that contains recognizable text
- **THEN** the system opens scanning details with the recognized text

#### Scenario: No text is recognized
- **WHEN** the user captures a frame with no recognizable text
- **THEN** the system displays a localized error and lets the user retry from the camera

### Requirement: User can review and extend a scan session
The system SHALL display accumulated recognized text as read-only numbered
Markdown sections on scanning details. It SHALL let the user add another scan,
append its recognized text as the next section, or return to Home and discard
the unfinished session.

#### Scenario: User adds another scan
- **WHEN** the user selects Add scan on scanning details and successfully captures text
- **THEN** the system displays the existing sections followed by the newly recognized section

#### Scenario: User returns to Home
- **WHEN** the user selects Back on scanning details
- **THEN** the system discards the unfinished session and returns to Home

#### Scenario: Configuration changes
- **WHEN** a configuration change occurs while an unfinished scanning session is open
- **THEN** the system retains the accumulated text in that session

#### Scenario: Session is unavailable after process death
- **WHEN** scanning details is restored after process death and its unfinished session is unavailable
- **THEN** the system displays a localized session-unavailable message and returns to Home

### Requirement: User can finish a scan session as Markdown
The system SHALL write every displayed Markdown section to a timestamped
Markdown file in app-private storage when the user selects Finish. It SHALL
display a localized success message containing the saved filename and return to
Home after a successful write.

#### Scenario: Markdown file is saved
- **WHEN** the user selects Finish for a session with accumulated text and the write succeeds
- **THEN** the app-private Markdown file contains all accumulated sections and the user receives the saved filename before returning to Home

#### Scenario: Markdown write fails
- **WHEN** the user selects Finish and the Markdown write fails
- **THEN** the system displays a localized error and discards the unfinished session

### Requirement: Scanning flow is localized
The system SHALL provide Russian and Uzbek Latin translations for all
user-visible scanning actions, permission explanations, errors, session status,
and save confirmation.

#### Scenario: Russian locale
- **WHEN** the app locale is Russian
- **THEN** all user-visible scanning flow text is rendered from Russian resources

#### Scenario: Uzbek Latin locale
- **WHEN** the app locale is Uzbek Latin
- **THEN** all user-visible scanning flow text is rendered from Uzbek Latin resources
