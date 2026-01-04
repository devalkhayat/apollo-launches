package com.example.apollo_launches.ui.screens.details

sealed interface LaunchDetailIntent {
    data class LoadLaunch(val launchId: String) : LaunchDetailIntent
    object Retry : LaunchDetailIntent
}