package com.example.domain

import com.example.domain.usecase.GetHomeTeamWinProbabilityUseCase
import com.example.domain.usecase.IsDrawUseCase
import com.example.domain.usecase.base.UseCaseResult
import com.example.domain.utils.getOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class GetHomeTeamWinProbabilityUseCaseTest {
    private val useCase = GetHomeTeamWinProbabilityUseCase()

    @Test
    fun `given home strength greater than away strength get win probability higher than draw`() =
        runBlocking {
            // Given
            val homeStrength = 78
            val awayStrength = 45

            // When
            val response = useCase.excecute(
                GetHomeTeamWinProbabilityUseCase.Params(
                    homeStrength,
                    awayStrength
                )
            )
            val data = response.getOrNull()

            // Then
            Assert.assertNotNull(response)
            Assert.assertTrue(response is UseCaseResult.Success)
            Assert.assertNotNull(data)
            Assert.assertTrue(data!! > IsDrawUseCase.DRAW_PROBABILITY)
        }

    @Test
    fun `given away strength greater than home strength get win probability lower than draw`() =
        runBlocking {
            // Given
            val homeStrength = 45
            val awayStrength = 78

            // When
            val response = useCase.excecute(
                GetHomeTeamWinProbabilityUseCase.Params(
                    homeStrength,
                    awayStrength
                )
            )
            val data = response.getOrNull()

            // Then
            Assert.assertNotNull(response)
            Assert.assertTrue(response is UseCaseResult.Success)
            Assert.assertNotNull(data)
            Assert.assertTrue(data!! < IsDrawUseCase.DRAW_PROBABILITY)
        }

    @Test
    fun `given away strength equals to home strength get draw probability`() =
        runBlocking {
            // Given
            val homeStrength = 68
            val awayStrength = 68

            // When
            val response = useCase.excecute(
                GetHomeTeamWinProbabilityUseCase.Params(
                    homeStrength,
                    awayStrength
                )
            )
            val data = response.getOrNull()

            // Then
            Assert.assertNotNull(response)
            Assert.assertTrue(response is UseCaseResult.Success)
            Assert.assertNotNull(data)
            Assert.assertEquals(IsDrawUseCase.DRAW_PROBABILITY, data!!, 0.0)
        }
}