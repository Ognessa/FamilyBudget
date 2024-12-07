package com.app.family_budget.details.model

import com.app.family_budget.domain.model.RepoDetailed
import java.time.LocalDateTime

data class RepoUi(
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
    val isFavourite: Boolean,
)

fun RepoDetailed.toUiModel(isFavourite: Boolean) = RepoUi(
    id = id,
    starsCount = starsCount,
    repoName = repoName,
    username = username,
    repoImageUrl = repoImageUrl,
    description = description,
    repoUrl = repoUrl,
    createdAt = createdAt,
    forksCount = forksCount,
    language = language,
    isFavourite = isFavourite,
)

fun RepoUi.toDomainModel() = RepoDetailed(
    id = id,
    starsCount = starsCount,
    repoName = repoName,
    username = username,
    repoImageUrl = repoImageUrl,
    description = description,
    repoUrl = repoUrl,
    createdAt = createdAt,
    forksCount = forksCount,
    language = language
)