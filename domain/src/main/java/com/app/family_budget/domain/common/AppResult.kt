package com.app.family_budget.domain.common

sealed class AppResult<T> {
    class Success<T>(val data: T) : AppResult<T>()
    class Error<T>(val error: AppError) : AppResult<T>()
}