package com.example.data.team.team.repositoryimpl

import com.example.data.team.team.datasource.TeamStaticDataSource
import com.example.data.team.team.mapper.TeamMapper
import com.example.domain.model.Team
import com.example.domain.repository.ITeamRepository

class TeamRepository(private val teamStaticDataSource: TeamStaticDataSource) : ITeamRepository {
    override fun getTeams(): List<Team> {
        return teamStaticDataSource.getTeams().let { teamResponse ->
            TeamMapper.fromTeamsStaticResponseToTeamModel(teamResponse)
        }
    }
}