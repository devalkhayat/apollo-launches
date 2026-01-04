package com.example.apollo_launches.data.network



import com.apollographql.apollo.ApolloClient
import com.example.apollo_launches.data.model.LaunchDetailDto

import com.example.apollo_launches.data.model.LaunchSummaryDto
import com.example.apollo_launches.data.model.MissionDetailDto
import com.example.apollo_launches.data.model.MissionSummaryDto
import com.example.apollo_launches.data.model.RocketDetailDto
import com.example.apollo_launches.data.model.RocketSummaryDto
import javax.inject.Inject
import javax.inject.Singleton
import com.example.apollo_launches.graphql.GetLaunchQuery
import com.example.apollo_launches.graphql.GetLaunchesQuery



@Singleton
class GraphQLService @Inject constructor(
    private val apolloClient: ApolloClient
) {



    // Fetch paginated list of launches
    suspend fun getLaunches(pageSize: Int, after: String?=null ): List<LaunchSummaryDto> {
        val response = apolloClient.query(
            GetLaunchesQuery(pageSize = pageSize )
        ).execute()

        val launches = response.data?.launches?.launches ?: emptyList()

        return launches.map { launch ->
            LaunchSummaryDto(
                id= launch?.id!! ,
                mission = MissionSummaryDto(name=launch?.mission?.name, missionPatch = launch?.mission?.missionPatch) ,
                rocket = RocketSummaryDto(name = launch?.rocket?.name!!)
            )
        }
    }


    // Fetch details of a single launch
    suspend fun getLaunchById(launchId: String): LaunchDetailDto {
        val response = apolloClient.query(
            GetLaunchQuery(id = launchId)
        ).execute()

        val launch = response.data?.launch ?: throw Exception("Launch not found")

        return LaunchDetailDto(
            id= launch?.id!! ,
            site = launch?.site,
            mission = MissionDetailDto(name=launch?.mission?.name, missionPatch = launch?.mission?.missionPatch) ,
            rocket = RocketDetailDto(id=launch?.rocket?.id!!,name = launch?.rocket?.name!!, type = launch?.rocket?.type!!)
        )
    }


}
