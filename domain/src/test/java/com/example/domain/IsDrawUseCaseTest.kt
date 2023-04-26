package com.example.domain

import com.example.domain.usecase.IsDrawUseCase
import com.example.domain.usecase.base.UseCaseResult
import com.example.domain.utils.getOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class IsDrawUseCaseTest {
    private val useCase = IsDrawUseCase()

    @Test
    fun `given draw probability get random response`() = runBlocking {
        // Given
        val probability = IsDrawUseCase.DRAW_PROBABILITY

        // When
        val dataList = mutableListOf<Boolean>()
        (1..10).forEach { _ ->
            val response = useCase.excecute(IsDrawUseCase.Params(probability))
            val data = response.getOrNull()

            // Then
            Assert.assertNotNull(response)
            Assert.assertTrue(response is UseCaseResult.Success)
            Assert.assertNotNull(data)
            Assert.assertTrue(data is Boolean)

            dataList.add(data!!)
        }

        Assert.assertTrue(dataList.any { !it }) // contains some false values
        Assert.assertTrue(dataList.any { it }) // contains some true values
    }

    @Test
    fun `given not draw probability get false`() = runBlocking {
        // Given
        val probability = Math.random()

        // When
        val response = useCase.excecute(IsDrawUseCase.Params(probability))
        val data = response.getOrNull()

        // Then
        Assert.assertNotNull(response)
        Assert.assertTrue(response is UseCaseResult.Success)
        Assert.assertNotNull(data)
        Assert.assertFalse(data!!)
    }
}