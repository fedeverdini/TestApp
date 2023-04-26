package com.example.domain

import com.example.domain.mocks.MockedModels.MATCH_A_B
import com.example.domain.usecase.GetHomeTeamWinProbabilityUseCase
import com.example.domain.usecase.GetScoresUseCase
import com.example.domain.usecase.IsDrawUseCase
import com.example.domain.usecase.SimulateMatchUseCase
import com.example.domain.usecase.base.UseCaseResult
import com.example.domain.utils.getOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stubbing

class SimulateMatchUseCaseTest {

    private val isDrawUseCase = mock<IsDrawUseCase>()
    private val getScoresUseCase = mock<GetScoresUseCase>()
    private val getHomeTeamWinProbabilityUseCase = mock<GetHomeTeamWinProbabilityUseCase>()

    private var useCase = SimulateMatchUseCase(
        isDrawUseCase = isDrawUseCase,
        getScoresUseCase = getScoresUseCase,
        getHomeTeamWinProbabilityUseCase = getHomeTeamWinProbabilityUseCase
    )

    @Before
    fun setUp() {
        stubbing(getHomeTeamWinProbabilityUseCase) {
            onBlocking {
                getHomeTeamWinProbabilityUseCase.excecute(any())
            } doReturn UseCaseResult.Success(Math.random())
        }

        stubbing(isDrawUseCase) {
            onBlocking {
                isDrawUseCase.excecute(any())
            } doReturn UseCaseResult.Success(false)
        }

        stubbing(getScoresUseCase) {
            onBlocking {
                getScoresUseCase.excecute(any())
            } doReturn UseCaseResult.Success(Pair(0, 0))
        }
    }

    @Test
    fun `given any match get match finished`() = runBlocking {
        // Given
        val match = MATCH_A_B

        // When
        val response = useCase.excecute(SimulateMatchUseCase.Params(match))
        val data = response.getOrNull()

        // Then
        Assert.assertNotNull(response)
        Assert.assertTrue(response is UseCaseResult.Success)
        Assert.assertNotNull(data)
        Assert.assertEquals(true, data?.isFinished)
    }

    @Test
    fun `given match with same strength get match finished as draw`() = runBlocking {
        // Given
        stubbing(getHomeTeamWinProbabilityUseCase) {
            onBlocking {
                getHomeTeamWinProbabilityUseCase.excecute(any())
            } doReturn UseCaseResult.Success(IsDrawUseCase.DRAW_PROBABILITY)
        }

        stubbing(isDrawUseCase) {
            onBlocking {
                isDrawUseCase.excecute(any())
            } doReturn UseCaseResult.Success(true)
        }

        stubbing(getScoresUseCase) {
            onBlocking {
                getScoresUseCase.excecute(any())
            } doReturn UseCaseResult.Success(Pair(4, 6))
        }

        // When
        val response = useCase.excecute(SimulateMatchUseCase.Params(MATCH_A_B))
        val data = response.getOrNull()

        // Then
        Assert.assertNotNull(response)
        Assert.assertTrue(response is UseCaseResult.Success)
        Assert.assertNotNull(data)
        Assert.assertEquals(true, data!!.isFinished)
        Assert.assertEquals(data.homeScore, data.awayScore)
    }
}