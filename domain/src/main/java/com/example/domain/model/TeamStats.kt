package com.example.domain.model

import com.example.domain.utils.getGoalAgainst
import com.example.domain.utils.getGoalFor
import com.example.domain.utils.getLostMatches
import com.example.domain.utils.getTiedMatches
import com.example.domain.utils.getWonMatches
import java.io.Serializable

data class TeamStats(
    val team: Team,
    val matches: List<Match>,
) : Serializable {

    val played = matches.count { match -> match.isFinished }
    val winCount = matches.getWonMatches(team)
    val drawCount = matches.getTiedMatches(team)
    val lossCount = matches.getLostMatches(team)
    val goalFor = matches.getGoalFor(team)
    val goalAgainst = matches.getGoalAgainst(team)
    val goalDiff = goalFor - goalAgainst
    val points = winCount * 3 + drawCount

    // Get head-to-head result between two teams
    fun getHeadToHeadResult(awayTeam: Team): Int {
        return matches.firstOrNull { match -> match.awayTeam == awayTeam }?.let { match ->
            match.homeScore - match.awayScore
        } ?: 0
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is TeamStats -> {
                this.points == other.points && this.goalDiff == other.goalDiff && this.goalFor == other.goalFor && this.goalDiff == other.goalDiff && this.goalAgainst == other.goalAgainst
            }

            else -> false
        }
    }

    override fun hashCode(): Int {
        var result = team.hashCode()
        result = 31 * result + matches.hashCode()
        result = 31 * result + winCount
        result = 31 * result + drawCount
        result = 31 * result + lossCount
        result = 31 * result + goalFor
        result = 31 * result + goalAgainst
        result = 31 * result + goalDiff
        result = 31 * result + points
        return result
    }
}
