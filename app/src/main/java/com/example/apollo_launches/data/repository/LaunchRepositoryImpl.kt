package com.example.apollo_launches.data.repository

import com.example.apollo_launches.data.mapper.LaunchMapper
import com.example.apollo_launches.data.network.GraphQLService
import com.example.apollo_launches.domain.model.LaunchDetail
import com.example.apollo_launches.domain.model.LaunchesPage
import com.example.apollo_launches.domain.repository.LaunchRepository
import jakarta.inject.Inject

class LaunchRepositoryImpl @Inject constructor(
    private val service: GraphQLService
) : LaunchRepository {

    override suspend fun getLaunches(pageSize: Int, after: String?): LaunchesPage {
        val dtos = service.getLaunches(pageSize, after)
        val launches = dtos.map { LaunchMapper.toLaunchSummary(it) }

        val cursor = dtos.lastOrNull()?.id
        val hasMore = dtos.size == pageSize

        return LaunchesPage(
            launches = launches ,
            cursor = cursor ,
            hasMore = hasMore
        )
    }

    override suspend fun getLaunchById(launchId: String): LaunchDetail {
        val dto = service.getLaunchById(launchId)
        return LaunchMapper.toLaunchDetail(dto)
    }
}
