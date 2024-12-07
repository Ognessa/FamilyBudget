package com.app.list.ui

import com.app.family_budget.domain.common.AppError
import com.app.family_budget.domain.model.Filter
import com.app.list.model.RepoUi

data class ListScreenState(
    val repoList: List<RepoUi> = listOf(),
    val searchQuery: String = "",
    val isLoading: Boolean = true,
    val currentFilter: Filter = Filter.LastMonth,
    val loadingNextPage: Boolean = false,
    val currentPage: Int = 0,
    val hasNextPage: Boolean = true,
    val error: AppError = AppError.None(),
)
