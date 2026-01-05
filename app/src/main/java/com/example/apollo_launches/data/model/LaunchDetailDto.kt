package com.example.apollo_launches.data.model

data class LaunchDetailDto(
    val id: String,
    val site: String?,
    val mission: MissionDetailDto?,
    val rocket: RocketDetailDto
)

data class MissionDetailDto(
    val name: String?,
    val missionPatch: String? // LARGE size
)

data class RocketDetailDto(
    val id: String,
    val name: String,
    val type: String
)



