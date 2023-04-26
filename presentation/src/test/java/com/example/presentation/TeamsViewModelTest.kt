package com.example.presentation

import com.example.domain.mocks.MockedModels
import com.example.domain.model.Match
import com.example.domain.model.Team
import com.example.domain.usecase.GetGroupsUseCase
import com.example.domain.usecase.GetTeamsUseCase
import com.example.domain.usecase.base.UseCaseResult
import com.example.domain.utils.getOrNull
import com.example.presentation.team.TeamsViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stubbing
import java.lang.Exception

class TeamsViewModelTest {

    companion object {
        private const val NUM_OF_TEAMS = 4
    }

    @get: Rule
    val coroutineRule = CoroutinesTestRule()

    private val getTeamsUseCase = mock<GetTeamsUseCase>()
    private val viewModel = TeamsViewModel(coroutineRule.testDispatcherProvider, getTeamsUseCase)

    @Test
    fun `given useCase call get list of teams`() = runBlocking {
        // Given
        stubbing(getTeamsUseCase) {
            onBlocking { getTeamsUseCase.excecute(any()) } doReturn UseCaseResult.Success(
                MockedModels.ALL_TEAMS.take(NUM_OF_TEAMS)
            )
        }

        // When
        viewModel.getTeams()
        val response = viewModel.state.value.teams

        // Then
        Assert.assertTrue(response.isNotEmpty())
        Assert.assertEquals(NUM_OF_TEAMS, response.size)
    }

    @Test
    fun `given useCase call get no teams error`() = runBlocking {
        // Given
        stubbing(getTeamsUseCase) {
            onBlocking { getTeamsUseCase.excecute(any()) } doReturn UseCaseResult.Error(
                error = Exception("No teams found")
            )
        }

        // When
        var event: TeamsViewModel.Event? = null
        val collectJob = launch(coroutineRule.testDispatcher) {
            viewModel.event.collect { event = it }
        }

        viewModel.getTeams()
        val response = viewModel.state.value.teams

        // Then
        Assert.assertTrue(response.isEmpty())
        Assert.assertTrue(event is TeamsViewModel.Event.NoTeamsEvent)

        collectJob.cancel()
    }
}