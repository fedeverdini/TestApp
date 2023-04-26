package com.example.domain.model

import java.io.Serializable

data class Match(
    val homeTeam: Team,
    val awayTeam: Team,
    var homeScore: Int = 0,
    var awayScore: Int = 0,
    val isFinished: Boolean = false
) : Serializable {
    fun getTeams() = listOf(homeTeam, awayTeam)
}
