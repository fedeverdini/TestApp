package com.example.domain.usecase.base

import java.lang.Exception

abstract class BaseUseCase<T, PARAMS> protected constructor() {

    protected abstract suspend fun buildUseCase(params: PARAMS): UseCaseResult<T>

    suspend fun excecute(params: PARAMS) = try {
        buildUseCase(params)
    } catch (e: Exception) {
        UseCaseResult.Error(error = e)
    }
}