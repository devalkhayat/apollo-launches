package com.example.apollo_launches.data.mapper

import com.example.apollo_launches.data.model.LaunchDetailDto
import com.example.apollo_launches.data.model.LaunchSummaryDto
import com.example.apollo_launches.domain.model.LaunchDetail
import com.example.apollo_launches.domain.model.LaunchSummary

object LaunchMapper {

    fun toLaunchSummary(dto: LaunchSummaryDto) = LaunchSummary(
        id = dto.id ,
        missionName = dto.mission?.name ,
        missionPatch = dto.mission?.missionPatch ,
        rocketName = dto.rocket.name
    )

    fun toLaunchDetail(dto: LaunchDetailDto) = LaunchDetail(
        id = dto.id ,
        site = dto.site ,
        missionName = dto.mission?.name ,
        missionPatch = dto.mission?.missionPatch ,
        rocketId = dto.rocket.id ,
        rocketName = dto.rocket.name ,
        rocketType = dto.rocket.type
    )
}
