package com.example.apollo_launches.ui.screens.launches

sealed class LaunchListIntent {
    object LoadLaunches : LaunchListIntent()
    data class Refresh(val pageSize: Int) : LaunchListIntent()
    data class LoadNextPage(val pageSize: Int) : LaunchListIntent()
}