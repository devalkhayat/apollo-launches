package com.example.apollo_launches.data.network




import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.example.apollo_launches.data.model.LaunchDetailDto
import com.example.apollo_launches.data.model.MissionDetailDto
import com.example.apollo_launches.data.model.RocketDetailDto
import javax.inject.Inject
import javax.inject.Singleton
import com.example.apollo_launches.graphql.GetLaunchQuery
import com.example.apollo_launches.graphql.GetLaunchesQuery



@Singleton
class GraphQLService @Inject constructor(
    private val apolloClient: ApolloClient
) {



    // Fetch paginated list of launches
    suspend fun getLaunches(
        pageSize: Int,
        after: String?
    ): ApolloResponse<GetLaunchesQuery.Data> {
        return apolloClient.query(
            GetLaunchesQuery(
                pageSize = Optional.present(pageSize),
                after = Optional.presentIfNotNull(after)
            )
        ).execute()
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
