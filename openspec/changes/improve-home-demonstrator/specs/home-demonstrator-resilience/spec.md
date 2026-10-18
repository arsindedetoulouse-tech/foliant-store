# Spec Delta

## Purpose

Keeps the Home demonstrator useful, readable and localized when cache persistence
or the selected application theme and locale differ from the default path.

## ADDED Requirements

### Requirement: Fresh Home data survives cache-write failure
When Home data has loaded successfully, the system SHALL return that fresh data
even if its local cache write fails. A cache-write failure SHALL NOT be rendered
as a Home loading error; it may be safely recorded for diagnostics without
including protected values.

#### Scenario: Fresh data loads but caching fails
- **WHEN** Home loading returns fresh data and saving it locally fails
- **THEN** Home displays the fresh data instead of a cached value or loading error

#### Scenario: Remote loading fails
- **WHEN** Home loading itself fails
- **THEN** Home preserves its existing cached-data fallback behavior

### Requirement: Home respects theme surface colors
The Home screen SHALL render its full screen on the active Material theme surface
so its content remains legible in both light and dark system themes.

#### Scenario: Dark system theme
- **WHEN** the application uses its dark theme
- **THEN** the full Home screen uses the dark Material surface behind its content

### Requirement: Home is localized for supported locales
The Home screen SHALL provide Russian and Uzbek Latin translations for every
user-visible Home title, hint, refresh action and loading-error message.

#### Scenario: Russian locale
- **WHEN** the application locale is Russian
- **THEN** Home user-visible text is rendered from Russian resources

#### Scenario: Uzbek Latin locale
- **WHEN** the application locale is Uzbek Latin
- **THEN** Home user-visible text is rendered from Uzbek Latin resources
