package com.example.domain.usecase

import com.example.domain.model.Match
import com.example.domain.usecase.base.BaseUseCase
import com.example.domain.usecase.base.UseCaseResult
import com.example.domain.utils.getOrNull

class SimulateMatchUseCase(
    private val isDrawUseCase: IsDrawUseCase,
    private val getScoresUseCase: GetScoresUseCase,
    private val getHomeTeamWinProbabilityUseCase: GetHomeTeamWinProbabilityUseCase
) : BaseUseCase<Match, SimulateMatchUseCase.Params>() {

    override suspend fun buildUseCase(params: Params): UseCaseResult<Match> = with(params) {
        // Calculate the probability of the home team winning based on their strength
        val homeTeamWinProbability = getHomeTeamWinProbabilityUseCase.excecute(
            GetHomeTeamWinProbabilityUseCase.Params(
                match.homeTeam.strength,
                match.awayTeam.strength
            )
        ).getOrNull() ?: Math.random()

        val isDraw = isDrawUseCase.excecute(
            IsDrawUseCase.Params(
                homeTeamWinProbability
            )
        ).getOrNull() ?: false

        val (homeScore, awayScore) = getScoresUseCase.excecute(
            GetScoresUseCase.Params(
                match,
                homeTeamWinProbability
            )
        ).getOrNull() ?: Pair(0, 0)

        return UseCaseResult.Success(
            match.copy(
                homeScore = homeScore,
                awayScore = if (isDraw) homeScore else awayScore,
                isFinished = true
            )
        )
    }

    data class Params(val match: Match)
}