package com.app.family_budget.domain.common


suspend fun <T> AppResult<T>.onSuccess(execute: suspend (AppResult.Success<T>) -> Unit): AppResult<T> {
    if (this is AppResult.Success) {
        execute(this)
    }
    return this
}

suspend fun <T> AppResult<T>.onError(execute: suspend (AppResult.Error<T>) -> Unit): AppResult<T> {
    if (this is AppResult.Error) {
        execute(this)
    }
    return this
}