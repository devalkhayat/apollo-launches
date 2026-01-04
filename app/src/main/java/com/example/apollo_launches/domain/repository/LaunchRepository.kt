package com.example.apollo_launches.domain.repository

import com.example.apollo_launches.domain.model.LaunchDetail
import com.example.apollo_launches.domain.model.LaunchesPage

interface LaunchRepository {
    // Get list of launches (summary) with pagination
    suspend fun getLaunches(pageSize: Int, after: String?): LaunchesPage

    // Get full details of a single launch
    suspend fun getLaunchById(launchId: String): LaunchDetail
}
