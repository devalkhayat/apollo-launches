package com.example.apollo_launches.domain.model

data class LaunchesPage(
    val launches: List<LaunchSummary>,
    val cursor: String?,
    val hasMore: Boolean
)
