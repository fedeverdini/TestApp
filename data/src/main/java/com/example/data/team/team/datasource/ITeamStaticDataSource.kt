package com.example.data.team.team.datasource

import com.example.data.team.team.response.TeamStaticResponse

interface ITeamStaticDataSource {
    fun getTeams(): TeamStaticResponse
}