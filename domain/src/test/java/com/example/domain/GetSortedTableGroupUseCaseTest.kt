package com.example.domain

import com.example.domain.mocks.MockedModels
import com.example.domain.usecase.GetSortedTableGroupUseCase
import com.example.domain.usecase.base.UseCaseResult
import com.example.domain.utils.getOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class GetSortedTableGroupUseCaseTest {
    private val useCase = GetSortedTableGroupUseCase()

    @Test
    fun `given team stats get sorted table group`() = runBlocking {
        // Given
        val tableGroup = MockedModels.TABLE_GROUP
        val expected = MockedModels.SORTED_TABLE_GROUP

        // When
        val response = useCase.excecute(GetSortedTableGroupUseCase.Params(tableGroup))
        val data = response.getOrNull()

        // Then
        Assert.assertNotNull(response)
        Assert.assertTrue(response is UseCaseResult.Success)
        Assert.assertNotNull(data)
        Assert.assertTrue(data!!.stats.isNotEmpty())
        Assert.assertEquals(expected, data)
    }

    // TODO: Add more test cases
}