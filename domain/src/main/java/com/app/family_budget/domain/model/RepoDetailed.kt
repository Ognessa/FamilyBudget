package com.app.family_budget.domain.model

import java.time.LocalDateTime

data class RepoDetailed(
    val id: Long,
    val starsCount: Int,
    val repoName: String,
    val username: String,
    val repoImageUrl: String,
    val description: String,
    val repoUrl: String,
    val createdAt: LocalDateTime,
    val forksCount: Long,
    val language: String?,
)
