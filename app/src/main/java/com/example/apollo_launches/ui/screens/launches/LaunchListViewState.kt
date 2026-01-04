package com.example.apollo_launches.ui.screens.launches

import com.example.apollo_launches.domain.model.LaunchSummary

data class LaunchListViewState(
    val isLoading: Boolean = false ,
    val launches: List<LaunchSummary> = emptyList() ,
    val error: String? = null,
    val hasMore: Boolean=true
)