package com.app.family_budget.domain

import com.app.family_budget.domain.common.AppResult
import com.app.family_budget.domain.model.RepoDetailed
import com.app.family_budget.domain.model.RepoListRequestParams
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getById(id: Long): AppResult<RepoDetailed?>
    suspend fun getTrending(params: RepoListRequestParams): AppResult<List<RepoDetailed>>
    suspend fun getFavourites(): Flow<List<RepoDetailed>>
    suspend fun addToFavourites(repoItem: RepoDetailed)
    suspend fun deleteFromFavourite(id: Long)
    suspend fun isFavourite(id: Long): Boolean
    suspend fun getCount(): Long
}