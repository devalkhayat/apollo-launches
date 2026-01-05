package com.example.apollo_launches.data.mapper

import com.example.apollo_launches.data.model.LaunchDetailDto
import com.example.apollo_launches.data.model.LaunchRemoteDto
import com.example.apollo_launches.domain.model.Launch
import com.example.apollo_launches.domain.model.LaunchDetail
import com.example.apollo_launches.graphql.GetLaunchesQuery


object LaunchMapper {

    fun GetLaunchesQuery.Launch.toRemoteDto(): LaunchRemoteDto =
        LaunchRemoteDto(
            id = id,
            missionName = mission?.name,
            missionPatchUrl = mission?.missionPatch,
            rocketName = rocket?.name
        )

    fun LaunchRemoteDto.toDomain(): Launch =
        Launch(
            id = id,
            missionName = missionName,
            missionPatchUrl = missionPatchUrl,
            rocketName = rocketName
        )

    fun LaunchDetailDto.toDomainLaunchDetail():LaunchDetail{
        return LaunchDetail(
            id = this.id ,
            missionName = this.mission?.name ,
            rocketName = this.rocket?.name ,
            site = this.site ,
            missionPatch = this.mission?.missionPatch ,
            rocketId = this.rocket?.id ,
            rocketType = this.rocket?.type ,
        )
    }



}


