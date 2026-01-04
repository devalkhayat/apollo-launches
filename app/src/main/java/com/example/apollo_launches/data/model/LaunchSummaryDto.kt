package com.example.apollo_launches.data.model

data class LaunchSummaryDto(
    val id: String,
    val mission: MissionSummaryDto?,
    val rocket: RocketSummaryDto
)