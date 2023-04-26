package com.example.domain.usecase

import com.example.domain.model.Match
import com.example.domain.model.Team
import com.example.domain.usecase.base.BaseUseCase
import com.example.domain.usecase.base.UseCaseResult

class GetGroupsUseCase : BaseUseCase<Map<Int, List<Match>>, GetGroupsUseCase.Params>() {

    override suspend fun buildUseCase(params: Params): UseCaseResult<Map<Int, List<Match>>> {
        val matches = getMatchCombinations(params.teams)
        return UseCaseResult.Success(getGroups(matches))
    }

    private fun getMatchCombinations(teams: List<Team>): List<Match> {
        val matches = mutableListOf<Match>()
        for (i in teams.indices) {
            val homeTeam = teams[i]
            val remainingElements = teams.subList(i.inc(), teams.size)
            if (remainingElements.isNotEmpty()) {
                remainingElements.forEach { awayTeam ->
                    matches.add(Match(homeTeam, awayTeam))
                }
                getMatchCombinations(remainingElements)
            }
        }
        return matches
    }

    private fun getGroups(matches: List<Match>, groupSize: Int = 2): Map<Int, List<Match>> {
        val groupQty = matches.size / groupSize
        val groups = mutableMapOf<Int, MutableList<Match>>()
        (1..groupQty).forEach {
            groups[it] = mutableListOf()
        }

        groups.forEach { group ->
            matches.forEach { match ->
                val isNotAssignedMatch = groups.flatMap { it.value }.contains(match).not()
                val isValidMatch = isValidMatchForGroup(match, group.value, groupSize)
                if (isNotAssignedMatch && isValidMatch) {
                    group.value.add(match)
                }
            }
        }

        return groups
    }

    private fun isValidMatchForGroup(
        match: Match,
        groupMatches: List<Match>,
        groupSize: Int = 2
    ): Boolean {
        if (groupMatches.size >= groupSize) return false
        val groupTeams = groupMatches.flatMap { listOf(it.homeTeam, it.awayTeam) }
        return !groupTeams.contains(match.homeTeam) && !groupTeams.contains(match.awayTeam)
    }

    data class Params(val teams: List<Team>, val groupSize: Int = 2)
}