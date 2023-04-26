package com.example.domain.usecase

import com.example.domain.model.Match
import com.example.domain.usecase.base.BaseUseCase
import com.example.domain.usecase.base.UseCaseResult

class GetScoresUseCase : BaseUseCase<Pair<Int, Int>, GetScoresUseCase.Params>() {

    override suspend fun buildUseCase(params: Params): UseCaseResult<Pair<Int, Int>> {
        with(params) {
            val random = Math.random()

            // Determine the winner based on the random number and the win probability
            val winner = if (random < homeTeamWinProbability) match.homeTeam else match.awayTeam

            // Generate the scores for the winner and loser
            val winnerScore = (random * (winner.strength / 2) % 10).toInt() + 1
            val loserScore = (random * (winnerScore / 2)).toInt()

            // Create the match result
            val homeScore = if (winner == match.homeTeam) winnerScore else loserScore
            val awayScore = if (winner == match.awayTeam) winnerScore else loserScore

            return UseCaseResult.Success(
                Pair(homeScore, awayScore)
            )
        }
    }

    data class Params(val match: Match, val homeTeamWinProbability: Double)
}