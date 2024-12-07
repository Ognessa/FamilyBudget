package com.app.family_budget.data.repository

import com.app.family_budget.data.local.dao.RepoDao
import com.app.family_budget.data.local.model.RepoEntity
import com.app.family_budget.data.local.model.toRepoDetailed
import com.app.family_budget.data.local.model.toRepoEntity
import com.app.family_budget.data.remote.GithubApiService
import com.app.family_budget.data.remote.model.DetailsResponse
import com.app.family_budget.data.remote.model.toRepoDetailed
import com.app.family_budget.domain.Repository
import com.app.family_budget.domain.common.AppDispatcher
import com.app.family_budget.domain.common.AppResult
import com.app.family_budget.domain.common.ExceptionHandler
import com.app.family_budget.domain.model.Filter
import com.app.family_budget.domain.model.RepoDetailed
import com.app.family_budget.domain.model.RepoListRequestParams
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RepositoryImpl(
    private val remoteSource: GithubApiService,
    private val localSource: RepoDao,
    private val appDispatcher: AppDispatcher,
    private val handler: ExceptionHandler,
) : Repository {
    override suspend fun getTrending(params: RepoListRequestParams): AppResult<List<RepoDetailed>> {
        return handler.handle(appDispatcher.IO) {
            remoteSource.searchRepositories(
                buildQuery(params.searchQuery, params.filter),
                params.sort,
                params.order,
                params.perPage,
                params.page,
            ).details.map(DetailsResponse::toRepoDetailed)
        }
    }

    override suspend fun getById(id: Long): AppResult<RepoDetailed?> {
        return handler.handle(appDispatcher.IO) {
            val localData = localSource.getById(id)?.toRepoDetailed()

            localData ?: remoteSource.getById(id).toRepoDetailed()
        }
    }

    override suspend fun getFavourites(): Flow<List<RepoDetailed>> {
        return withContext(appDispatcher.IO) {
            localSource.getFavourites().map { data -> data.map(RepoEntity::toRepoDetailed) }
        }
    }

    override suspend fun addToFavourites(repoItem: RepoDetailed) {
        withContext(appDispatcher.IO) {
            val entity: RepoEntity = repoItem.toRepoEntity()
            localSource.insertAll(entity)
        }
    }

    override suspend fun deleteFromFavourite(id: Long) {
        return withContext(appDispatcher.IO) {
            localSource.delete(id)
        }
    }

    override suspend fun isFavourite(id: Long): Boolean {
        return withContext(appDispatcher.IO) {
            localSource.isExists(id)
        }
    }

    override suspend fun getCount(): Long {
        return withContext(appDispatcher.IO) {
            localSource.getCount()
        }
    }

    private fun buildQuery(query: String, filter: Filter): String {

        val createdFrom: LocalDateTime = when (filter) {
            Filter.LastMonth -> LocalDateTime.now().minusMonths(1)

            Filter.LastWeek -> LocalDateTime.now().minusWeeks(1)

            Filter.LastDay -> LocalDateTime.now().minusDays(1)

            else -> return query
        }

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val createdFromFormatted = createdFrom.format(formatter)

        val newQuery: String = if (query.isBlank()) {
            "created:>$createdFromFormatted"
        } else {
            try {
                val textQuery =
                    URLEncoder.encode(query.trim { it <= ' ' }, StandardCharsets.UTF_8.name())
                "$textQuery+created:>$createdFromFormatted"
            } catch (e: Exception) {
                "created:>$createdFromFormatted"
            }
        }

        return newQuery
    }
}