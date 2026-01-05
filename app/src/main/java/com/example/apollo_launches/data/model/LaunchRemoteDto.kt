package com.example.apollo_launches.data.model


data class LaunchRemoteDto(
    val id: String,
    val missionName: String?,
    val missionPatchUrl: String?,
    val rocketName: String?
)