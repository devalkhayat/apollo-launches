package com.example.apollo_launches.domain.model

data class LaunchSummary(
    val id: String,
    val missionName: String?,
    val missionPatch: String?,   // SMALL patch
    val rocketName: String
)

