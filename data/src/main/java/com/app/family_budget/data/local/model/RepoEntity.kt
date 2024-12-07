package com.app.family_budget.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.family_budget.data.local.model.RepoEntity.Companion.TABLE_NAME
import com.app.family_budget.domain.model.RepoDetailed
import java.time.LocalDateTime
import java.time.ZoneOffset

@Entity(tableName = TABLE_NAME)
data class RepoEntity(

    @PrimaryKey
    @ColumnInfo(name = ID)
    val id: Long,

    @ColumnInfo(name = STARS_COUNT)
    val starsCount: Int,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = CREATED_AT)
    val createdAtEpochSec: Long,

    @ColumnInfo(name = "forks_count")
    val forksCount: Long,

    @ColumnInfo(name = "language")
    val language: String?,

    @ColumnInfo(name = "login")
    val login: String,

    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,
) {
    companion object {
        const val ID: String = "id"
        const val TABLE_NAME: String = "repo_table"
        const val CREATED_AT: String = "created_at"
        const val STARS_COUNT: String = "stars_count"
    }
}

fun RepoEntity.toRepoDetailed(): RepoDetailed {
    val dateTime = LocalDateTime.ofEpochSecond(createdAtEpochSec, 0, ZoneOffset.UTC)

    return RepoDetailed(
        id = id,
        starsCount = starsCount,
        repoName = name,
        username = login,
        repoImageUrl = avatarUrl,
        description = description,
        repoUrl = url,
        createdAt = dateTime,
        forksCount = forksCount,
        language = language
    )
}

fun RepoDetailed.toRepoEntity() = RepoEntity(
    id = id,
    starsCount = starsCount,
    url = repoUrl,
    name = username,
    description = description,
    createdAtEpochSec = createdAt.toEpochSecond(ZoneOffset.UTC),
    forksCount = forksCount,
    language = language,
    login = username,
    avatarUrl = repoImageUrl,
)