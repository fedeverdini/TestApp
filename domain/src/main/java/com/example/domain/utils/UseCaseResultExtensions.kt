package com.example.domain.utils

import com.example.domain.usecase.base.UseCaseResult

fun <T> UseCaseResult<T>.getOrNull(): T? {
    return if(this is UseCaseResult.Success) data else null
}