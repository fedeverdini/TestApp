package com.example.domain

import com.example.domain.mocks.MockedModels.TEAM_A
import com.example.domain.mocks.MockedModels.TEAM_B
import com.example.domain.mocks.MockedModels.TEAM_C
import com.example.domain.mocks.MockedModels.TEAM_D
import com.example.domain.model.Match
import com.example.domain.usecase.GetGroupsUseCase
import com.example.domain.utils.getOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class GetGroupsUseCaseTest {

    private val useCase = GetGroupsUseCase()

    @Test
    fun `given list of teams get team groups`() = runBlocking {
        // Given
        val teams = listOf(TEAM_A, TEAM_B, TEAM_C, TEAM_D)
        val expected = mapOf(
            1 to listOf(Match(TEAM_A, TEAM_B, 0, 0), Match(TEAM_C, TEAM_D)),
            2 to listOf(Match(TEAM_A, TEAM_C, 0, 0), Match(TEAM_B, TEAM_D)),
            3 to listOf(Match(TEAM_A, TEAM_D, 0, 0), Match(TEAM_B, TEAM_C)),
        )

        // When
        val response = useCase.excecute(GetGroupsUseCase.Params(teams)).getOrNull()

        // Then
        Assert.assertNotNull(response)
        Assert.assertEquals(expected, response)
    }
}