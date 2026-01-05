package com.example.apollo_launches.ui.screens.launches


sealed class LaunchIntent {
    object LoadLaunches : LaunchIntent()          // Load first page
    object RefreshLaunches : LaunchIntent()       // Pull-to-refresh
}