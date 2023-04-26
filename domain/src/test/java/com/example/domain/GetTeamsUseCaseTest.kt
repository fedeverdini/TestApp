package com.example.domain

import com.example.domain.mocks.MockedModels.ALL_TEAMS
import com.example.domain.repository.ITeamRepository
import com.example.domain.usecase.GetTeamsUseCase
import com.example.domain.usecase.base.UseCaseResult
import com.example.domain.utils.getOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stubbing

class GetTeamsUseCaseTest {

    private val teamsRepository = mock<ITeamRepository>()
    private val useCase = GetTeamsUseCase(teamsRepository)

    @Test
    fun `given number of teams get team list`() = runBlocking {
        // Given
        stubbing(teamsRepository) {
            on { it.getTeams() } doReturn ALL_TEAMS
        }

        // When
        val response = useCase.excecute(GetTeamsUseCase.Params(NUM_OF_TEAMS))
        val data = response.getOrNull()

        // Then

        Assert.assertNotNull(response)
        Assert.assertTrue(response is UseCaseResult.Success)
        Assert.assertNotNull(data)
        Assert.assertEquals(NUM_OF_TEAMS, data?.size)
    }

    @Test
    fun `given number of teams get no teams error`() = runBlocking {
        // Given
        stubbing(teamsRepository) {
            on { it.getTeams() } doReturn emptyList()
        }

        // When
        val response = useCase.excecute(GetTeamsUseCase.Params(NUM_OF_TEAMS))
        val errorMessage = (response as? UseCaseResult.Error)?.error?.message

        // Then
        Assert.assertNotNull(response)
        Assert.assertTrue(response is UseCaseResult.Error)
        Assert.assertNotNull("No teams found", errorMessage)
    }

    companion object {
        private const val NUM_OF_TEAMS = 4
    }
}