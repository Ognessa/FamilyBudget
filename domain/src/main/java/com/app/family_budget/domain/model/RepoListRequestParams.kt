package com.app.family_budget.domain.model

data class RepoListRequestParams(
    val searchQuery: String,
    val perPage: Int,
    val page: Int,
    val sort: String,
    val order: String,
    val filter: Filter,
)
