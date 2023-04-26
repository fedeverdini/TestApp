package com.example.domain

import com.example.domain.mocks.MockedModels
import com.example.domain.usecase.GetScoresUseCase
import com.example.domain.usecase.base.UseCaseResult
import com.example.domain.utils.getOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class GetScoresUseCaseTest {
    private val useCase = GetScoresUseCase()

    @Test
    fun `given match and max win probability get home team as winner`() =
        runBlocking {
            // Given
            val homeTeamProbability = 1.0

            // When
            val response = useCase.excecute(
                GetScoresUseCase.Params(
                    MockedModels.MATCH_A_B,
                    homeTeamProbability
                )
            )
            val data = response.getOrNull()

            // Then
            Assert.assertNotNull(response)
            Assert.assertTrue(response is UseCaseResult.Success)
            Assert.assertNotNull(data)
            Assert.assertTrue(data!!.first > data.second)
        }

    @Test
    fun `given match and min win probability get away team as winner`() =
        runBlocking {
            // Given
            val homeTeamProbability = 0.0

            // When
            val response = useCase.excecute(
                GetScoresUseCase.Params(
                    MockedModels.MATCH_A_B,
                    homeTeamProbability
                )
            )
            val data = response.getOrNull()

            // Then
            Assert.assertNotNull(response)
            Assert.assertTrue(response is UseCaseResult.Success)
            Assert.assertNotNull(data)
            Assert.assertTrue(data!!.second > data.first)
        }

    @Test
    fun `given same match twice and any probability get random score each time`() =
        runBlocking {
            // Given
            val homeTeamProbability = Math.random()

            // When
            val firstResponse = useCase.excecute(
                GetScoresUseCase.Params(
                    MockedModels.MATCH_A_B,
                    homeTeamProbability
                )
            )
            val firstData = firstResponse.getOrNull()

            val secondResponse = useCase.excecute(
                GetScoresUseCase.Params(
                    MockedModels.MATCH_A_B,
                    homeTeamProbability
                )
            )
            val secondData = secondResponse.getOrNull()

            // Then
            Assert.assertNotNull(firstResponse)
            Assert.assertTrue(firstResponse is UseCaseResult.Success)
            Assert.assertNotNull(firstData)

            Assert.assertNotNull(secondResponse)
            Assert.assertTrue(secondResponse is UseCaseResult.Success)
            Assert.assertNotNull(secondData)

            Assert.assertNotEquals(firstResponse, secondResponse)
            Assert.assertNotEquals(firstData, secondData)
        }
}