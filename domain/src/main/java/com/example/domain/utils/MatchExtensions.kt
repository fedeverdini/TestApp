package com.example.domain.utils

import com.example.domain.model.Match
import com.example.domain.model.Team

fun List<Match>.getWonMatches(team: Team): Int {
    return this.count { match ->
        when {
            match.isFinished && match.homeTeam == team -> match.homeScore > match.awayScore
            match.isFinished && match.awayTeam == team -> match.awayScore > match.homeScore
            else -> false
        }
    }
}

fun List<Match>.getLostMatches(team: Team): Int {
    return this.count { match ->
        when {
            match.isFinished && match.homeTeam == team -> match.awayScore > match.homeScore
            match.isFinished && match.awayTeam == team -> match.homeScore > match.awayScore
            else -> false
        }
    }
}

fun List<Match>.getTiedMatches(team: Team): Int {
    return this.count { match ->
        match.isFinished &&
                match.homeScore == match.awayScore &&
                listOf(match.homeTeam, match.awayTeam).contains(team)
    }
}

fun List<Match>.getGoalFor(team: Team): Int {
    return this.sumOf { match ->
        when {
            match.homeTeam == team -> match.homeScore
            match.awayTeam == team -> match.awayScore
            else -> 0
        }
    }
}

fun List<Match>.getGoalAgainst(team: Team): Int {
    return this.sumOf { match ->
        when {
            match.homeTeam == team -> match.awayScore
            match.awayTeam == team -> match.homeScore
            else -> 0
        }
    }
}