package com.app.family_budget.domain.common

sealed interface AppError {
    class ApiLimit : AppError
    class Http(val code: Int) : AppError
    class Unknown : AppError
    class None : AppError
}