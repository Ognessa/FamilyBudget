package com.app.family_budget.data.remote.model

import androidx.annotation.Keep
import com.app.family_budget.domain.model.RepoDetailed
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Serializable
@Keep
data class DetailsResponse(
    @SerialName("id")
    val id: Long,
    @SerialName("stargazers_count")
    val stargazersCount: Int,
    @SerialName("forks")
    val forks: Long,
    @SerialName("html_url")
    val url: String,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String?,
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("owner")
    val owner: OwnerResponse,
    @SerialName("forks_count")
    val forksCount: Long,
    @SerialName("language")
    val language: String?,
)

fun DetailsResponse.toRepoDetailed(): RepoDetailed {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX")
    val dateTime = LocalDateTime.parse(createdAt, formatter)

    return RepoDetailed(
        id = this.id,
        starsCount = this.stargazersCount,
        repoName = this.name,
        username = this.owner.login,
        repoImageUrl = this.owner.avatarUrl,
        description = this.description ?: "",
        repoUrl = this.url,
        createdAt = dateTime,
        forksCount = forksCount,
        language = language
    )
}