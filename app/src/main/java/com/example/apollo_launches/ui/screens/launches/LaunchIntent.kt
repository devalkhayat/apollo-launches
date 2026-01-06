package com.example.apollo_launches.ui.screens.launches


sealed interface LaunchIntent {
    data object ScreenOpened : LaunchIntent
    data object Retry : LaunchIntent
    data object Refresh : LaunchIntent
}