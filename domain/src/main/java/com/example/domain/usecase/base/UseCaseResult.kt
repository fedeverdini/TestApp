package com.example.domain.usecase.base

import java.lang.Exception

sealed class UseCaseResult<out T> {
    class Success<out T>(val data: T): UseCaseResult<T>()
    class Error(val error: Exception): UseCaseResult<Nothing>()
}
