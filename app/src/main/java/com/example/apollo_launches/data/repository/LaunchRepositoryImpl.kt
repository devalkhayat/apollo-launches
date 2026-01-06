package com.example.apollo_launches.data.repository

import com.example.apollo_launches.data.mapper.LaunchMapper.toDomain
import com.example.apollo_launches.data.mapper.LaunchMapper.toDomainLaunchDetail
import com.example.apollo_launches.data.mapper.LaunchMapper.toRemoteDto
import com.example.apollo_launches.data.network.GraphQLService
import com.example.apollo_launches.domain.model.LaunchDetail
import com.example.apollo_launches.domain.paging.CursorPage
import com.example.apollo_launches.domain.repository.LaunchRepository
import jakarta.inject.Inject

class LaunchRepositoryImpl @Inject constructor(
    private val service: GraphQLService
) : LaunchRepository {

    override suspend fun getLaunchById(launchId: String): LaunchDetail {
        val dto = service.getLaunchById(launchId)
        return dto.toDomainLaunchDetail()
    }

    override suspend fun getLaunches(
        pageSize: Int,
        cursor: String?
    ): CursorPage {

        val response = service.getLaunches(pageSize, cursor)

        val connection = response.data!!.launches

        val items = connection.launches
            .map { it?.toRemoteDto() }   // data
            .map { it?.toDomain() }      // domain

        return CursorPage(
            items = items  ,
            nextCursor = connection.cursor ,
            hasMore = connection.hasMore
        )
    }


}



