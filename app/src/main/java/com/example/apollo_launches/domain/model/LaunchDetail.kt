package com.example.apollo_launches.domain.model

data class LaunchDetail(
    val id: String,
    val site: String?,
    val missionName: String?,
    val missionPatch: String?,
    val rocketId: String?,
    val rocketName: String?,
    val rocketType: String?
)
