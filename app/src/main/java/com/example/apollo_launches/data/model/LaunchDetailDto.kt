package com.example.apollo_launches.data.model

data class LaunchDetailDto(
    val id: String,
    val site: String?,
    val mission: MissionDetailDto?,
    val rocket: RocketDetailDto
)



