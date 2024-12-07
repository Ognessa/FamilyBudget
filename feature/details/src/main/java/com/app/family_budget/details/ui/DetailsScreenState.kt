package com.app.family_budget.details.ui

import com.app.family_budget.details.model.RepoUi
import com.app.family_budget.domain.common.AppError

data class DetailsScreenState(
    val repoUi: RepoUi? = null,
    val error: AppError = AppError.None()
)