Stress Meter Assignment Submission
=====================================

Student: Dyk Kyong Do
Assignment: StressMeter Lab 3

Implementation Notes:
- This assignment implements a mobile stress meter app using Android Studio
- The app uses MVVM architecture with LiveData
- Features include image grid selection, stress level scoring, data visualization with charts
- Uses MPAndroidChart library for data visualization
- Implements navigation drawer with fragments
- Includes vibration and sound feedback on app start
- Data is stored in CSV format (stress_timestamp.csv)
- Images are shuffled randomly on app startup and when returning from background

Technical Implementation:
- MainActivity: Hosts fragments and manages navigation drawer
- StressMeterFragment: Displays image grid for stress level selection
- ResultFragment: Shows chart visualization and data table
- ConfirmSelectionActivity: Handles image confirmation and scoring
- ImageManager: Singleton for managing image shuffling
- VibrationAndSound: Utility for haptic and audio feedback
- ResultsAdapter: RecyclerView adapter for data display

Dependencies:
- MPAndroidChart for chart visualization
- Material Design Components
- AndroidX libraries

No external resources or class examples were used as the foundation for this implementation.
