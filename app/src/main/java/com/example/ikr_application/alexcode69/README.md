


# Alexcode69 Package - Time Entry Manager

## Overview
This package demonstrates a complete MVVM architecture implementation with reactive data handling using Kotlin Flow. It implements a time entry management system with search/filter capabilities.

## Architecture

### Layer Structure
- **Data Layer** (`data/`): Repository with fake time entry data
- **Domain Layer** (`domain/`): Business logic with multiple UseCase classes
- **UI Layer** (`ui/`): Fragment with ViewModel for reactive UI updates

### Key Components

#### Domain Layer
- `CurrentDateUseCase`: Provides current system date
- `ElapsedTimeUseCase`: Calculates elapsed time with different precisions
- `SearchTimeEntriesUseCase`: Filters time entries based on search query (Flow-based)
- `AddTimeEntryUseCase`: Adds new time entries to repository
- `TimePrecisions`: Enum for time unit conversions (ms, s, m, h)

#### Data Layer
- `DeviceRepository`: Manages time entries with MutableStateFlow
- `TimeEntry`: Data model for time tracking
- `DeviceInfo`: Device system information model

#### UI Layer
- `MyViewModel`: Reactive view model using Flow<TimerUiState>
- `Alexcode69Fragment`: Fragment with Flow collection and UI updates
- `TimerUiState`: State object containing UI data (entries, date, search query, loading state)

## Key Features

### Reactive Data Flow
```
Repository (StateFlow) 
    ↓
UseCase (Flow.map for filtering)
    ↓
ViewModel (flatMapLatest + combine for state management)
    ↓
Fragment (collectAsState + repeatOnLifecycle for UI updates)
```

### Search/Filter Functionality
- Real-time search on time entries
- Case-insensitive filtering
- Updates automatically when search query changes

### External Dependencies
- **Timber** (v4.7.1): Logging library for debugging
  - Usage: `Timber.d()`, `Timber.e()`, etc.
  - No LogCat pollution, formatted logs

- **Coil** (v3.3.0): Image loading library
  - HTTP networking support
  
- **MPAndroidChart** (v3.1.0): Chart and graph library
  - For visualization capabilities

## Usage

### Adding a Time Entry
```kotlin
viewModel.addTimeEntry("Work Session")
```

### Searching Entries
User can type in the search field, which automatically updates the displayed entries through Flow.

### Reactive UI Updates
The Fragment collects `viewModel.uiState` Flow and updates UI whenever state changes:
```kotlin
viewLifecycleOwner.lifecycleScope.launch {
    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.uiState.collect { state ->
            // Update UI based on state
        }
    }
}
```

## Technical Details

### No LiveData
✅ Uses Flow and StateFlow exclusively
✅ No LiveData dependency
✅ Coroutine-based reactive architecture

### Concurrency
- Heavy operations use `viewModelScope.launch` with `withContext(Dispatchers.IO)` if needed
- All repository operations are synchronous for simplicity (can be made suspend)
- UI updates happen on Main dispatcher automatically via Flow collection

### State Management
- Central `TimerUiState` object holds all UI state
- Single source of truth via StateFlow in ViewModel
- Fragment reacts to state changes, not internal UI state

## Requirements Met ✅

1. ✅ Custom package structure (data, domain, ui)
2. ✅ Repository with fake data
3. ✅ Multiple UseCase classes
4. ✅ Fragment with ViewModel
5. ✅ XML-based UI with proper styling
6. ✅ Data mapping from ViewModel to View
7. ✅ No LiveData usage - Flow/StateFlow only
8. ✅ Interactive search element
9. ✅ External library usage (Timber + existing Coil, MPAndroidChart)
10. ✅ Reactive state management with Flow

## Building and Testing

The package is fully integrated with the main app. To test:
1. Run the app
2. Navigate to "ALEXCODE69" in the screens list
3. Use the search field to filter time entries
4. Check Logcat for Timber logs (tag: ALEXCODE69)

## Notes

- All classes in the alexcode69 package are completely independent from nfirex
- Timber logging helps with debugging flow updates and user interactions
- State is preserved during configuration changes via ViewModel scope
