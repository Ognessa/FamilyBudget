package com.app.family_budget.data.remote

import com.app.family_budget.data.remote.model.DetailsResponse
import com.app.family_budget.data.remote.model.GithubRepoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {

    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query(value = "q", encoded = true) query: String?,
        @Query("sort") sort: String?,
        @Query("order") order: String?,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): GithubRepoResponse

    @GET("repositories/{id}")
    suspend fun getById(
        @Path("id") id: Long
    ): DetailsResponse
}