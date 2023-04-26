package com.example.domain.usecase

import com.example.domain.usecase.base.BaseUseCase
import com.example.domain.usecase.base.UseCaseResult

class IsDrawUseCase : BaseUseCase<Boolean, IsDrawUseCase.Params>() {

    companion object {
        const val DRAW_PROBABILITY = 0.5
    }

    override suspend fun buildUseCase(params: Params): UseCaseResult<Boolean> {
        return UseCaseResult.Success(
            params.probability == DRAW_PROBABILITY && Math.random() > DRAW_PROBABILITY
        )
    }

    data class Params(val probability: Double)
}