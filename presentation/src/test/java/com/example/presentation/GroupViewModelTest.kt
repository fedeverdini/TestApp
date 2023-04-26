package com.example.presentation

import com.example.domain.mocks.MockedModels.GROUPS
import com.example.domain.mocks.MockedModels.MATCH_A_B
import com.example.domain.mocks.MockedModels.SIMULATED_MATCH_A_B
import com.example.domain.mocks.MockedModels.TEAM_A
import com.example.domain.mocks.MockedModels.TEAM_B
import com.example.domain.mocks.MockedModels.TEAM_C
import com.example.domain.mocks.MockedModels.TEAM_D
import com.example.domain.usecase.GetGroupsUseCase
import com.example.domain.usecase.SimulateMatchUseCase
import com.example.domain.usecase.base.UseCaseResult
import com.example.presentation.group.GroupsViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stubbing

class GroupViewModelTest {
    @get: Rule
    val coroutineRule = CoroutinesTestRule()

    private val getGroupsUseCase = mock<GetGroupsUseCase>()
    private val simulateMatchUseCase = mock<SimulateMatchUseCase>()

    private val viewModel = GroupsViewModel(
        coroutineRule.testDispatcherProvider,
        getGroupsUseCase,
        simulateMatchUseCase
    )

    @Test
    fun `given list of teams get groups`() = runBlocking {
        // Given
        stubbing(getGroupsUseCase) {
            onBlocking { getGroupsUseCase.excecute(any()) } doReturn UseCaseResult.Success(
                GROUPS
            )
        }

        val teams = listOf(TEAM_A, TEAM_B, TEAM_C, TEAM_D)

        val expectedGroupQty = 3
        val expectedMatchesPerGroup = 2

        // When
        viewModel.getGroups(teams)
        val response = viewModel.state.value.groups

        // Then
        Assert.assertTrue(response.isNotEmpty())
        Assert.assertEquals(expectedGroupQty, response.size)
        response.forEach { group ->
            Assert.assertFalse(group.isComplete)
            Assert.assertEquals(expectedMatchesPerGroup, group.matches.size)
        }
    }

    @Test
    fun `given match get simulated result`() = runBlocking {
        // Given
        stubbing(getGroupsUseCase) {
            onBlocking { getGroupsUseCase.excecute(any()) } doReturn UseCaseResult.Success(
                GROUPS
            )
        }
        stubbing(simulateMatchUseCase) {
            onBlocking { simulateMatchUseCase.excecute(any()) } doReturn UseCaseResult.Success(
                SIMULATED_MATCH_A_B
            )
        }

        val groupId = 1
        val match = MATCH_A_B
        val expectedMatch = SIMULATED_MATCH_A_B
        val teams = listOf(TEAM_A, TEAM_B, TEAM_C, TEAM_D)

        // When
        var event: GroupsViewModel.Event? = null
        val collectJob = launch(coroutineRule.testDispatcher) {
            viewModel.event.collect { event = it }
        }

        viewModel.getGroups(teams)
        viewModel.simulateMatch(groupId, match)
        val response = viewModel.state.value.groups

        // Then
        Assert.assertTrue(response.isNotEmpty())
        Assert.assertNotNull(
            response
                .firstOrNull { it.groupId == groupId }
                ?.matches
                ?.firstOrNull { it == expectedMatch }
        )
        Assert.assertNull(event)

        collectJob.cancel()
    }

    // TODO: add more test cases
}