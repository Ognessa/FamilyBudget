package com.app.family_budget.data.remote.model

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class GithubRepoResponse(
    @SerialName("items")
    val details: List<DetailsResponse>
)
