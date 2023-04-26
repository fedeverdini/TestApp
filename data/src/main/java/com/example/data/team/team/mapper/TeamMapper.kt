package com.example.data.team.team.mapper

import com.example.data.team.team.response.TeamStaticResponse
import com.example.domain.model.Team

object TeamMapper {
    fun fromTeamsStaticResponseToTeamModel(response: TeamStaticResponse): List<Team> {
        return response.teams.takeUnless { it.isEmpty() }?.let { teams ->
            teams.filterNot { teamResponse ->
                teamResponse.name.isNullOrBlank() || teamResponse.strength == null
            }.map { teamResponse ->
                Team(teamResponse.name!!, teamResponse.strength!!)
            }
        } ?: emptyList()
    }
}