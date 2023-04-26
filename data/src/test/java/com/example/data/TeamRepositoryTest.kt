package com.example.data

import com.example.data.team.team.datasource.TeamStaticDataSource
import com.example.data.team.team.mapper.TeamMapper
import com.example.data.team.team.repositoryimpl.TeamRepository
import com.example.data.team.team.response.TeamResponse
import com.example.data.team.team.response.TeamStaticResponse
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stubbing

class TeamRepositoryTest {

    private val repositoryDataSource = mock<TeamStaticDataSource>()

    private val teamRepository = TeamRepository(repositoryDataSource)

    @Test
    fun `given static datasource valid response get mapped team list`() = runBlocking {
        // Given
        stubbing(repositoryDataSource) {
            on { it.getTeams() } doReturn VALID_DATA_SOURCE_RESPONSE
        }

        val expected = TeamMapper.fromTeamsStaticResponseToTeamModel(VALID_DATA_SOURCE_RESPONSE)

        // When
        val response = teamRepository.getTeams()

        // Then
        Assert.assertArrayEquals(expected.toTypedArray(), response.toTypedArray())
    }

    @Test
    fun `get static datasource empty response get empty list`() = runBlocking {
        // Given
        stubbing(repositoryDataSource) {
            on { it.getTeams() } doReturn EMPTY_DATA_SOURCE_RESPONSE
        }

        val expected = TeamMapper.fromTeamsStaticResponseToTeamModel(EMPTY_DATA_SOURCE_RESPONSE)

        // When
        val response = teamRepository.getTeams()

        // Then
        Assert.assertArrayEquals(expected.toTypedArray(), response.toTypedArray())
    }

    companion object {
        private val EMPTY_DATA_SOURCE_RESPONSE = TeamStaticResponse(emptyList())

        private val VALID_DATA_SOURCE_RESPONSE = TeamStaticResponse(
            teams = listOf(
                TeamResponse("Team A", 1),
                TeamResponse("Team B", 2),
                TeamResponse("Team C", 3),
                TeamResponse("Team D", 4),
            )
        )
    }
}