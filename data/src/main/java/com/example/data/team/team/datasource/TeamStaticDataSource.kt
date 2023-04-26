package com.example.data.team.team.datasource

import com.example.data.team.team.response.TeamStaticResponse
import com.example.data.team.team.utils.StaticJsonResponse
import com.google.gson.Gson

class TeamStaticDataSource : ITeamStaticDataSource {
    override fun getTeams(): TeamStaticResponse {
        val jsonTeam = StaticJsonResponse.JSON_TEAM
        return Gson().fromJson(jsonTeam, TeamStaticResponse::class.java)
    }
}