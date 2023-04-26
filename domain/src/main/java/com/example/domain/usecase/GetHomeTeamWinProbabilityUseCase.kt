package com.example.domain.usecase

import com.example.domain.usecase.base.BaseUseCase
import com.example.domain.usecase.base.UseCaseResult

class GetHomeTeamWinProbabilityUseCase :
    BaseUseCase<Double, GetHomeTeamWinProbabilityUseCase.Params>() {

    override suspend fun buildUseCase(params: Params): UseCaseResult<Double> {
        return UseCaseResult.Success(
            getHomeTeamWinProbability(
                params.homeTeamStrength,
                params.awayTeamStrength
            )
        )
    }

    private fun getHomeTeamWinProbability(homeTeamStrength: Int, awayTeamStrength: Int): Double {
        val strongestProbability =
            1.0 * maxOf(homeTeamStrength, awayTeamStrength) / (homeTeamStrength + awayTeamStrength)

        return when {
            homeTeamStrength > awayTeamStrength -> 1.5 * strongestProbability
            homeTeamStrength == awayTeamStrength -> IsDrawUseCase.DRAW_PROBABILITY
            else -> 1.0 - strongestProbability
        }
    }

    data class Params(val homeTeamStrength: Int, val awayTeamStrength: Int)
}