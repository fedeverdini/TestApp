package com.example.presentation.group.adapter

import com.example.domain.model.Match
import com.example.domain.model.Team
import com.example.domain.model.TeamStats

object GroupUIMapper {
    fun fromMapGroupToGroupUIModel(map: Map<Int, List<Match>>): List<GroupUIModel> {
        return map.map { group ->
            GroupUIModel(group.key, group.value)
        }
    }

    fun fromGroupUIModelListToTeamStats(team: Team, groups: List<GroupUIModel>): TeamStats {
        val teamMatches = groups.flatMap { group ->
            group.matches.filter { match ->
                match.getTeams().contains(team)
            }
        }
        return TeamStats(team = team, matches = teamMatches)
    }
}