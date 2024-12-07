package com.app.family_budget.data.common

import android.util.Log
import com.app.family_budget.domain.common.AppError
import com.app.family_budget.domain.common.AppResult
import com.app.family_budget.domain.common.ExceptionHandler
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class ExceptionHandlerImpl : ExceptionHandler {
    override suspend fun <T> handle(
        dispatcher: CoroutineContext,
        callable: suspend () -> T,
    ): AppResult<T> {
        return withContext(dispatcher) {
            try {
                AppResult.Success(callable())
            } catch (e: HttpException) {
                Log.d(this.javaClass.simpleName, e.localizedMessage ?: e.stackTraceToString())

                if (e.code() == 403) {
                    AppResult.Error(AppError.ApiLimit())
                } else {
                    AppResult.Error(AppError.Http(e.code()))
                }

            } catch (e: Exception) {

                AppResult.Error(AppError.Unknown())
            }
        }
    }
}