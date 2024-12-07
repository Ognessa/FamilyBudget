package com.app.family_budget.domain.common

import kotlin.coroutines.CoroutineContext

interface ExceptionHandler {
    suspend fun <T> handle(
        dispatcher: CoroutineContext,
        callable: suspend () -> T,
    ): AppResult<T>
}